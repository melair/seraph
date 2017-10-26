package net.melaircraft.seraph.server.data.value;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringValueTest {
    @Test
    public void testOutput() {
        StringValue value = new StringValue("hello");
        assertEquals("hello", value.output());
    }
}