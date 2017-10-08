package net.melaircraft.seraph.display.output.seraph.operations;

import java.io.IOException;
import java.nio.ByteBuffer;

public class SetPosition implements Operation {
    private final int x;
    private final int y;

    public SetPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void output(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.put((byte) 2);
        byteBuffer.putShort((byte) x);
        byteBuffer.putShort((byte) y);
    }

    @Override
    public int getSize() {
        return 5;
    }
}
