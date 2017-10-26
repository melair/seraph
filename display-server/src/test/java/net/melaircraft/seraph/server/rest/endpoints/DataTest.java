package net.melaircraft.seraph.server.rest.endpoints;

import net.melaircraft.seraph.server.data.DataManager;
import net.melaircraft.seraph.server.data.Entry;
import net.melaircraft.seraph.server.data.value.BooleanValue;
import net.melaircraft.seraph.server.data.value.NumberValue;
import net.melaircraft.seraph.server.data.value.StringValue;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

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
}
