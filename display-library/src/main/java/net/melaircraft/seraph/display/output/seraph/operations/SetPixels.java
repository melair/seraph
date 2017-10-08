package net.melaircraft.seraph.display.output.seraph.operations;

import net.melaircraft.seraph.display.PixelColour;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SetPixels implements Operation {
    private final int bytesAllowed;
    private List<PixelColour> pixels = new ArrayList<>();

    public SetPixels(int bytesAllowed) {
        this.bytesAllowed = bytesAllowed;
    }

    public boolean addPixel(PixelColour pixelColour) {
        if (getSize() + 3 <= bytesAllowed) {
            pixels.add(pixelColour);

            return true;
        }

        return false;
    }

    @Override
    public void output(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.put((byte) 4);
        byteBuffer.putShort((short) pixels.size());

        for (PixelColour c : pixels) {
            byteBuffer.put((byte) c.getRed());
            byteBuffer.put((byte) c.getGreen());
            byteBuffer.put((byte) c.getBlue());
        }
    }

    @Override
    public int getSize() {
        return 1 + 2 + (pixels.size() * 3);
    }
}
