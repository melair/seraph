package net.melaircraft.seraph.server.data;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DataManagerTest {
    @Test
    public void testGetMakesEntry() {
        DataManager dataManager = new DataManager();

        Entry entry = dataManager.get("test");
        assertNotNull(entry);

        assertEquals(entry, dataManager.get("test"));
    }

    @Test
    public void testListOfEntries() {
        DataManager dataManager = new DataManager();

        assertEquals(0, dataManager.getEntryNames().size());

        dataManager.get("test");
        Set<String> names = dataManager.getEntryNames();

        assertEquals(1, names.size());
        assertTrue(names.contains("test"));

    }
}