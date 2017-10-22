package net.melaircraft.seraph.server.rest;

import net.melaircraft.seraph.server.config.model.RestConfiguration;
import net.melaircraft.seraph.server.exceptions.RestException;
import org.junit.Test;

import static org.junit.Assert.fail;

public class RestManagerTest {
    @Test
    public void testReusedPortThrowsException() {
        RestConfiguration restConfiguration = new RestConfiguration();

        RestManager restManagerOne = new RestManager(restConfiguration, dataManager);

        try {
            RestManager restManagerTwo = new RestManager(restConfiguration, dataManager);
        } catch (RestException e) {
            return;
        } finally {
            restManagerOne.stop();
        }

        fail("No exception thrown for reused port!");
    }
}