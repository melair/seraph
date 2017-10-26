package net.melaircraft.seraph.server.data.value;

public class NumberValue implements Value {
    private final Number value;

    public NumberValue(Number value) {
        this.value = value;
    }

    @Override
    public String output() {
        return value.toString();
    }
}
