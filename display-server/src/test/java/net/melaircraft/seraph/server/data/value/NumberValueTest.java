package net.melaircraft.seraph.server.data.value;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberValueTest {
    @Test
    public void testOutput() {
        NumberValue value = new NumberValue(3.142);
        assertEquals("3.142", value.output());
    }
}