package net.melaircraft.seraph.server.data;

import net.melaircraft.seraph.server.data.value.StringValue;
import net.melaircraft.seraph.server.data.value.Value;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EntryTest {
    @Test
    public void testAddAndFetchValues() {
        StringValue value = new StringValue("value");
        Entry entry = new Entry();

        entry.add("key", value);
        assertEquals(value, entry.get("key"));

        StringValue value2 = new StringValue("value2");
        entry.add("key", value2);

        assertEquals(value2, entry.get("key"));
    }

    @Test
    public void testGetHashMap() {
        StringValue value = new StringValue("value");
        Entry entry = new Entry();

        entry.add("key", value);

        Map<String, Value> values = entry.getMap();
        assertEquals(value, values.get("key"));
    }

}