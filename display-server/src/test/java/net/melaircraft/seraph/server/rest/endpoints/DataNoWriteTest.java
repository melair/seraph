package net.melaircraft.seraph.server.rest.endpoints;

import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;

public class DataNoWriteTest extends RestTest {
    @Override
    public Set<Object> getSingletons() {
        return Collections.singleton(new Data(null, false));
    }

    @Test
    public void testWriteFails() {
        request
            .accept("application/json")
            .contentType("application/json")
            .body("{}")
            .put("/data/name")
        .then()
            .statusCode(405)
            .body("error.message", equalTo("Writes are not enabled."));
    }
}
