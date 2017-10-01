package net.melaircraft.seraph.display.output;

import net.melaircraft.seraph.display.Displayable;
import net.melaircraft.seraph.display.PixelColour;
import net.melaircraft.seraph.display.exception.InvalidPixelColourException;
import net.melaircraft.seraph.display.exception.NonExistentPixelException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PixelPusher implements Displayable, Runnable {
    private final int width;
    private final int height;
    private final InetAddress server;
    private final int port;

    private final PixelColour[][] output;
    private final boolean rowChange[];

    private final DatagramSocket clientSocket;

    private int sequence = 0;

    public PixelPusher(int width, int height, InetAddress server, int port) throws SocketException {
        this.width = width;
        this.height = height;
        this.server = server;
        this.port = port;

        this.output = new PixelColour[width][height];
        this.rowChange = new boolean[height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                output[x][y] = PixelColour.BLACK;
            }

            rowChange[y] = true;
        }

        clientSocket = new DatagramSocket();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setPixel(int x, int y, int r, int g, int b) throws NonExistentPixelException, InvalidPixelColourException {
        output[x][y] = new PixelColour(r, g, b);
        rowChange[y] = true;
    }

    @Override
    public PixelColour getPixel(int x, int y) throws NonExistentPixelException {
        return null;
    }

    public void setNextKeyFrame() {
        for (int y = 0; y < height; y++) {
            rowChange[y] = true;
        }
    }

    @Override
    public void run() {
        int mtu = 1460;
        int stripsPerPacket = (mtu - 4) / (1 + (3 * width));

        List<Integer> strips = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            if (rowChange[y]) {
                strips.add(y);

                if (strips.size() >= stripsPerPacket) {
                    sendStrips(strips);
                    strips.clear();
                }
            }
        }

        if (strips.size() > 0) {
            sendStrips(strips);
        }
    }

    private void sendStrips(Collection<Integer> strips) {
        if (strips.size() > 1) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream w = new DataOutputStream(baos);

                w.writeInt(sequence++);

                strips.forEach((y)-> {
                    rowChange[y] = false;

                    try {
                        w.writeByte(y);

                        for (int x = 0; x < width; x++) {
                            PixelColour colour = output[x][y];
                            w.writeByte(colour.getRed());
                            w.writeByte(colour.getGreen());
                            w.writeByte(colour.getBlue());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                w.flush();

                byte[] result = baos.toByteArray();

                DatagramPacket sendPacket = new DatagramPacket(result, result.length, server, port);
                clientSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
