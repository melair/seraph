package net.melaircraft.seraph.server.config;

import net.melaircraft.seraph.server.config.model.DisplayConfiguration;
import net.melaircraft.seraph.server.config.model.RestConfiguration;
import net.melaircraft.seraph.server.exceptions.InvalidConfigurationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ConfigurationManagerTest {
    @Test
    public void testDisplayLoad() {
        System.setProperty("config.dir", "src/test/resources/config/");
        DisplayConfiguration display = new ConfigurationManager().getDisplayConfiguration();

        assertEquals("1.2.3.4", display.getHostname());
        assertEquals(7777, display.getPort());
        assertEquals(128, display.getWidth());
        assertEquals(64, display.getHeight());

        assertEquals(1450, display.getMTU());
        assertEquals(20, display.getFrameRate());
    }

    @Test
    public void testDisplayLoadWithOverridesForDefaults() {
        System.setProperty("config.dir", "src/test/resources/config-with-default-overrides/");
        DisplayConfiguration display = new ConfigurationManager().getDisplayConfiguration();

        assertEquals("1.2.3.4", display.getHostname());
        assertEquals(7777, display.getPort());
        assertEquals(128, display.getWidth());
        assertEquals(64, display.getHeight());

        assertEquals(2000, display.getMTU());
        assertEquals(100, display.getFrameRate());
    }

    @Test
    public void testRestLoadWithOverridesForDefaults() {
        System.setProperty("config.dir", "src/test/resources/config-with-default-overrides/");
        RestConfiguration rest = new ConfigurationManager().getRestConfiguration();

        assertEquals("not-localhost", rest.getHost());
        assertFalse(rest.isEnabled());
        assertEquals(800, rest.getPort());
        assertFalse(rest.isAllowWrite());
    }


    @Test(expected = InvalidConfigurationException.class)
    public void testDisplayLoadWrongDirectory() {
        System.setProperty("config.dir", "/notvalid/");
        new ConfigurationManager().getDisplayConfiguration();
    }
}