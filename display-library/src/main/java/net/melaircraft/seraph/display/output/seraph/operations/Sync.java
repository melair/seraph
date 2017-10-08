package net.melaircraft.seraph.display.output.seraph.operations;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Sync implements Operation {
    @Override
    public void output(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.put((byte) 0);
    }

    @Override
    public int getSize() {
        return 1;
    }
}
