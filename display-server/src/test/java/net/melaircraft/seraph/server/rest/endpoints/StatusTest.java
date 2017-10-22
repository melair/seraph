package net.melaircraft.seraph.server.rest.endpoints;

import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;

public class StatusTest extends RestTest {
    @Override
    public Set<Object> getSingletons() {
        return Collections.singleton(new Status());
    }

    @Test
    public void testStatusReturnsOK() {
        request
            .get("/status")
        .then()
            .statusCode(200)
            .body("status", equalTo("OK"));
    }
}
