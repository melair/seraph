package net.melaircraft.seraph.server.rest.endpoints;

import net.melaircraft.seraph.server.data.DataManager;
import net.melaircraft.seraph.server.data.Entry;
import net.melaircraft.seraph.server.data.value.BooleanValue;
import net.melaircraft.seraph.server.data.value.NumberValue;
import net.melaircraft.seraph.server.data.value.StringValue;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;

public class DataTest extends RestTest {
    private DataManager dataManager = new DataManager();

    @Override
    public Set<Object> getSingletons() {
        return Collections.singleton(new Data(dataManager, true));
    }

    @Test
    public void testList() {
        Entry test = dataManager.get("test");
        test.add("string", new StringValue("string"));
        test.add("value", new NumberValue(3.142));
        test.add("boolean", new BooleanValue(true));

        request
            .accept("application/json")
            .get("/data")
        .then()
            .statusCode(200)
            .body("test.string", equalTo("string"))
            .body("test.value", equalTo(3.142F))
            .body("test.boolean", equalTo(true));
    }

    @Test
    public void testWriteFailsDueToInvalidJSON() {
        request
            .accept("application/json")
            .contentType("application/json")
            .body("ABC")
            .put("/data/name")
        .then()
            .statusCode(400)
            .body("error.message", equalTo("JSON provided is not valid."));
    }

    @Test
    public void testWriteFailsDueToNestedObject() {
        request
            .accept("application/json")
            .contentType("application/json")
            .body("{ \"a\": 2, \"b\": {} }")
            .put("/data/name")
        .then()
            .statusCode(400)
            .body("error.message", equalTo("Data must only contain primitive values."));
    }

    @Test
    public void testWriteSucceeds() {
        request
            .accept("application/json")
            .contentType("application/json")
            .body("{ \"string\": \"value\", \"boolean\": true, \"number\": 3.142 }")
            .put("/data/name")
        .then()
            .statusCode(200)
            .body("status", equalTo("Data accepted."));

        Entry entry = dataManager.get("name");

        assertEquals("value", entry.get("string").getRaw());
        assertEquals(true, entry.get("boolean").getRaw());
        assertEquals(3.142F, (Double) entry.get("number").getRaw(), 0.001F);
    }
}
