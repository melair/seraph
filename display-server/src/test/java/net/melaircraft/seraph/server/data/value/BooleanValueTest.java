package net.melaircraft.seraph.server.data.value;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BooleanValueTest {
    @Test
    public void testOutput() {
        BooleanValue value = new BooleanValue(true);
        assertEquals("true", value.output());
    }
}