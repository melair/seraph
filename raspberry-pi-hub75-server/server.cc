#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <errno.h>
#include <stdint.h>

#include "led-matrix.h"
#include "transformer.h"

using namespace rgb_matrix;

static const int kDefaultUDPPacketSize = 1460;
static const int kDefaultUDPPort = 7777;

RGBMatrix *matrix;
FrameCanvas *offscreen;
int cursor = 0;

volatile bool interrupt_received = false;
static void InterruptHandler(int signo) {
    interrupt_received = true;
}

static int usage(const char *progname) {
    fprintf(stderr, "usage: %s <options>\n", progname);
    fprintf(stderr, "Options:\n"
                    "\t-u <udp-size> : Max UDP data/packet (default %d)\n"
                    "\t                Best use the maximum that works with your network.\n"
                    "\t-d            : run as daemon. Use this when starting in /etc/init.d\n"
                    "\t-U            : Panel with each chain arranged in an sidways U. This gives you double the height and half the width.\n"
                    "\t-R <rotation> : Rotate display by given degrees (steps of 90).\n"
                    "\t-p <port>     : Set port to listen on (default %d)",
            kDefaultUDPPacketSize, kDefaultUDPPort);

    rgb_matrix::PrintMatrixFlags(stderr);

    return 1;
}

struct Colour {
    uint8_t red;
    uint8_t green;
    uint8_t blue;
};

int parsePacketSync(char *buffer, int bufferLen) {
    FrameCanvas *current = offscreen;
    offscreen = matrix->SwapOnVSync(offscreen);
    offscreen->CopyFrom(*current);
    return 0;
}

int parsePacketFill(char *buffer, int bufferLen) {
    if (bufferLen < (int) sizeof(Colour)) {
        return -1;
    }

    Colour *colour = (Colour *) buffer;
    offscreen->Fill(colour->red, colour->green, colour->blue);
    return sizeof(Colour);
}

void checkCursor() {
    if (cursor >= (matrix->width() * matrix->height()) {
        cursor = 0;
    }
}

int parsePacketPositionSet(char *buffer, int bufferLen) {
    if (bufferLen < (int) (sizeof(uint16_t) * 2)) {
        return -1;
    }

    uint16_t *x = (uint16_t *) buffer;
    uint16_t *y = (uint16_t *) buffer[sizeof(uint16_t)];

    cursor = (*y * matrix->width()) + *x;
    checkCursor();
    return sizeof(uint16_t) * 2);
}

int parsePacketPositionAdvance(char *buffer, int bufferLen) {
    if (bufferLen < (int) (sizeof(uint8_t))) {
        return -1;
    }

    cursor += (uint8_t) buffer;
    checkCursor();
    return sizeof(uint8_t);
}

int parsePacketSet(char *buffer, int bufferLen) {
    if (bufferLen < (int) (sizeof(uint16_t))) {
        return -1;
    }

    uint16_t *pixels = (uint16 *) buffer;

    int totalSize = sizeof(uint16_t) + (*pixels * sizeof(Colour));

    if (bufferLen < totalSize) {
        return -1;
    }

    int pos = sizeof(uint16_t);

    for (int i = 0; i < *pixels; i++) {
        Colour *colour = (Colour *) buffer[pos];

        int y = cursor / matrix->width();
        int x = cursor - (y * matrix->width());

        matrix->SetPixel(x, y, colour->red, colour->green, colour->blue);

        pos += sizeof(Colour)
        cursor++;
        checkCursor();
    }

    return totalSize;
}

void parsePacket(char *buffer, int bufferLen) {
    int pos = 0;
    cursor = 0;

    while (pos < bufferLen) {
        char opcode = buffer[pos++];

        int remaining = bufferLen - pos;
        int delta;

        switch (opcode) {
            case 0:
                delta = parsePacketSync(&buffer[pos], remaining);
                break;
            case 1:
                delta = parsePacketFill(&buffer[pos], remaining);
                break;
            case 2:
                delta = parsePacketPositionSet(&buffer[pos], remaining);
                break;
            case 3:
                delta = parsePacketPositionAdvance(&buffer[pos], remaining);
                break;
            case 4:
                delta = parsePacketSet(&buffer[pos], remaining);
                break;
            default:
                fprintf(stderr, "Unrecognised op code, %d.", opcode);
                return;
        }

        if (delta < 0) {
            fprintf(stderr, "Failed to correctly parse op code %d.", opcode);
            return;
        }

        pos += delta;
    }
}

int server(int port, int mtu, RGBMatrix *matrix) {
    int fd;

    if ((fd = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) == -1) {
        fprintf(stderr, "Could not obtain UDP socket.");
        return 1;
    }

    struct sockaddr_in local;
    bzero(&local, sizeof(local));

    local.sin_family = AF_INET;
    local.sin_port = htons(port);
    local.sin_addr.s_addr = htonl(INADDR_ANY);

    if(bind(fd, (struct sockaddr *) &local, sizeof(local)) == -1) {
        fprintf(stderr, "Could not bind UDP socket to port.");
        return 1;
    }

    struct timeval tv;
    tv.tv_sec = 1;
    tv.tv_usec = 0;

    if (setsockopt(fd, SOL_SOCKET, SO_RCVTIMEO, &tv, sizeof(tv)) < 0) {
        fprintf(stderr, "Failed to set receive timeout!");
        return 1;
    }

    char buffer[mtu];
    bzero(&buffer, sizeof(buffer));

    struct sockaddr_in remote;
    unsigned int remote_length = sizeof(remote);
    bzero(&remote, sizeof(remote));

    int recv_len = 0;

    int last_sequence = -1;

    while (!interrupt_received) {
        if ((recv_len = recvfrom(fd, buffer, mtu, 0, (struct sockaddr *) &remote, &remote_length)) == -1) {
            if (errno != EWOULDBLOCK) {
                fprintf(stderr, "Failed to receive UDP packet.");
                return 1;
            }

            continue;
        }

        if (recv_len < 5) {
            fprintf(stderr, "Ignoring packet, too short.");
        }

        int sequence = (int) buffer;

        if (sequence < last_sequence) {
            fprintf(stderr, "Packet out of order - this is %d, last was %d.", sequence, last_sequence);
        }

        last_sequence = sequence;

        parsePacket(&buffer[4], recv_len - 4);
    }

    close(fd);

    return 0;
}

int main(int argc, char *argv[]) {
    bool ushape_display = false;  // 64x64
    int rotation = 0;

    RGBMatrix::Options matrix_options;
    matrix_options.rows = 32;
    matrix_options.chain_length = 1;
    matrix_options.parallel = 1;
    rgb_matrix::RuntimeOptions runtime_opt;

    int udpSize = kDefaultUDPPacketSize;
    int udpPort = kDefaultUDPPort;

    if (!ParseOptionsFromFlags(&argc, &argv, &matrix_options, &runtime_opt)) {
        return usage(argv[0]);
    }

    int opt;
    while ((opt = getopt(argc, argv, "dUR:u:p:")) != -1) {
        switch (opt) {
            case 'd':
                runtime_opt.daemon = 1;
                break;
            case 'U':
                ushape_display = true;
                break;
            case 'R':
                rotation = atoi(optarg);
                break;
            case 'u':
                udpSize = atoi(optarg);
                break;
            case 'p':
                udpPort = atoi(optarg);
            default:
                return usage(argv[0]);
        }
    }

    if (getuid() != 0) {
        fprintf(stderr, "Must run as root to be able to access /dev/mem\n"
                "Prepend 'sudo' to the command:\n\tsudo %s ...\n", argv[0]);
        return 1;
    }

    matrix = CreateMatrixFromOptions(matrix_options, runtime_opt);

    if (ushape_display) {
        matrix->ApplyStaticTransformer(UArrangementTransformer(matrix_options.parallel));
    }

    if (rotation > 0) {
        matrix->ApplyStaticTransformer(RotateTransformer(rotation));
    }

    offscreen = matrix->CreateFrameCanvas();

    signal(SIGTERM, InterruptHandler);
    signal(SIGINT, InterruptHandler);

    return server(udpPort, udpSize, matrix);
}


