package net.melaircraft.seraph.display.output.seraph.operations;

import java.io.IOException;
import java.nio.ByteBuffer;

public class AdvancePosition implements Operation {
    private final int delta;

    public AdvancePosition(int delta) {
        this.delta = delta;
    }

    @Override
    public void output(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.put((byte) 3);
        byteBuffer.put((byte) delta);
    }

    @Override
    public int getSize() {
        return 2;
    }
}
