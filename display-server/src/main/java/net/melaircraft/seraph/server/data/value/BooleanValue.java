package net.melaircraft.seraph.server.data.value;

public class BooleanValue implements Value<Boolean> {
    private final Boolean value;

    public BooleanValue(Boolean value) {
        this.value = value;
    }

    @Override
    public String output() {
        return value.toString();
    }

    @Override
    public Boolean getRaw() {
        return value;
    }
}
