package net.melaircraft.seraph.display.output.seraph;

import net.melaircraft.seraph.display.CheckedDestinationDisplay;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.output.seraph.operations.AdvancePosition;
import net.melaircraft.seraph.display.output.seraph.operations.Fill;
import net.melaircraft.seraph.display.output.seraph.operations.SetPixels;
import net.melaircraft.seraph.display.output.seraph.operations.SetPosition;
import net.melaircraft.seraph.display.output.seraph.operations.Sync;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeraphProtocol implements CheckedDestinationDisplay, Runnable {
    private static final int FULL_FRAME_INTERVAL = 5000;

    private final int width;
    private final int height;

    private final InetAddress server;
    private final int port;
    private final int mtu;

    private final boolean[][] changedOutput;
    private final PixelColour[][] output;

    private final DatagramSocket clientSocket;

    private long lastFullFrame = 0;
    private int sequence = 0;

    public SeraphProtocol(int width, int height, InetAddress server, int port, int mtu) throws SocketException {
        this.width = width;
        this.height = height;
        this.server = server;
        this.port = port;
        this.mtu = mtu;

        this.output = new PixelColour[width][height];
        this.changedOutput = new boolean[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                output[x][y] = PixelColour.BLACK;
                changedOutput[x][y] = false;
            }
        }

        clientSocket = new DatagramSocket();
    }

    @Override
    public void run() {
        boolean fullFrame = false;

        if (lastFullFrame + FULL_FRAME_INTERVAL < System.currentTimeMillis()) {
            lastFullFrame = System.currentTimeMillis();

            fullFrame = true;
        }

        List<Pixel> pixelsToProcess = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((fullFrame && !output[x][y].equals(PixelColour.BLACK))|| changedOutput[x][y]) {
                    pixelsToProcess.add(new Pixel(x, y, output[x][y]));
                    changedOutput[x][y] = false;
                }
            }
        }

        if (pixelsToProcess.size() < 1) {
            return;
        }

        Collections.sort(pixelsToProcess);

        List<Packet> packets = new ArrayList<>();
        Packet currentPacket = null;
        SetPixels currentSetPixels = null;

        int cursor = 0;
        Pixel pixel = pixelsToProcess.remove(0);

        while (pixel != null) {
            if (currentPacket == null) {
                currentPacket = new Packet(mtu, sequence++);
                packets.add(currentPacket);

                if (fullFrame) {
                    currentPacket.add(new Fill(PixelColour.BLACK));
                    fullFrame = false;
                }

                currentPacket.add(new SetPosition(pixel.getX(), pixel.getY()));
                cursor = (pixel.getY() * width) + pixel.getX();

                currentSetPixels = new SetPixels(currentPacket.getRemainingBytes());
                currentPacket.add(currentSetPixels);
            }

            int pixelDesiredCursor = (pixel.getY() * width) + pixel.getX();

            if (pixelDesiredCursor != cursor) {
                if (currentPacket.getRemainingBytes() < (5 + 5)) {
                    currentPacket = null;
                    continue;
                }

                int diff = pixelDesiredCursor - cursor;

                if (diff < 256) {
                    currentPacket.add(new AdvancePosition(diff));
                } else {
                    currentPacket.add(new SetPosition(pixel.getX(), pixel.getY()));
                }

                cursor = (pixel.getY() * width) + pixel.getX();

                currentSetPixels = new SetPixels(currentPacket.getRemainingBytes());
                currentPacket.add(currentSetPixels);
            }

            if (!currentSetPixels.addPixel(pixel.getPixelColour())) {
                currentPacket = null;
                continue;
            }

            cursor++;

            if (pixelsToProcess.size() > 0) {
                pixel = pixelsToProcess.remove(0);
            } else {
                if (currentPacket.getRemainingBytes() < 1) {
                    currentPacket = new Packet(mtu, sequence++);
                    packets.add(currentPacket);
                }

                currentPacket.add(new Sync());
                break;
            }
        }

        for (Packet packet : packets) {
            try {
                byte[] result = packet.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(result, result.length, server, port);
                clientSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setActualPixel(int x, int y, PixelColour pixelColour) {
        if (pixelColour.equals(output[x][y])) {
            return;
        }

        output[x][y] = pixelColour;
        changedOutput[x][y] = true;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
