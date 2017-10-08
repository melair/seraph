package net.melaircraft.seraph.display.output.seraph.operations;

import net.melaircraft.seraph.display.PixelColour;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Fill implements Operation {
    private final PixelColour colour;

    public Fill(PixelColour colour) {
        this.colour = colour;
    }

    @Override
    public void output(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) colour.getRed());
        byteBuffer.put((byte) colour.getGreen());
        byteBuffer.put((byte) colour.getBlue());
    }

    @Override
    public int getSize() {
        return 4;
    }
}
