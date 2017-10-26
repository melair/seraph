package net.melaircraft.seraph.server.data;

import net.melaircraft.seraph.server.data.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Entry {
    private final Map<String, Value> values = new ConcurrentHashMap<>();

    public void add(String key, Value value) {
        values.put(key, value);
    }

    public Value get(String key) {
        return values.get(key);
    }

    public Map<String, Value> getMap() {
        return new HashMap<>(values);
    }
}
