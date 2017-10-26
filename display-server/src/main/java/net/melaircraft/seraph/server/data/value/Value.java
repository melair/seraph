package net.melaircraft.seraph.server.data.value;

public interface Value<T> {
    String output();
    T getRaw();
}