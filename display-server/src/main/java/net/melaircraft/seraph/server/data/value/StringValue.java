package net.melaircraft.seraph.server.data.value;

public class StringValue implements Value {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String output() {
        return value;
    }
}
