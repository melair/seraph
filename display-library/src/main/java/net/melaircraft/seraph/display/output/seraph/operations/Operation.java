package net.melaircraft.seraph.display.output.seraph.operations;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Operation {
    void output(ByteBuffer byteBuffer) throws IOException;

    int getSize();
}
