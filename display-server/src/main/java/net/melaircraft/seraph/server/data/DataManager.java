package net.melaircraft.seraph.server.data;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {
    private Map<String, Entry> values = new ConcurrentHashMap<>();

    public Entry get(String name) {
        return values.computeIfAbsent(name, s -> new Entry());
    }

    public Set<String> getEntryNames() {
        return values.keySet();
    }
}
