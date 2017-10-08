package net.melaircraft.seraph.display.output.seraph;

import net.melaircraft.seraph.display.output.seraph.operations.Operation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class Packet {
    private final int mtu;
    private final int sequence;
    private final List<Operation> operations = new ArrayList<>();

    public Packet(int mtu, int sequence) {
        this.mtu = mtu;
        this.sequence = sequence;
    }

    public boolean add(Operation operation) {
        if (operation.getSize() <= getRemainingBytes()) {
            operations.add(operation);
            return true;
        }

        return false;
    }

    public int getRemainingBytes() {
        return mtu - operations.stream().mapToInt(Operation::getSize).sum() - 4;
    }

    public byte[] toByteArray() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(operations.stream().mapToInt(Operation::getSize).sum() + 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        byteBuffer.putInt(sequence);

        for (Operation operation : operations) {
            operation.output(byteBuffer);
        }

        return byteBuffer.array();
    }
}
