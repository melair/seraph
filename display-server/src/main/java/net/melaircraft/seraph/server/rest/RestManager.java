package net.melaircraft.seraph.server.rest;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.melaircraft.seraph.server.config.model.RestConfiguration;
import net.melaircraft.seraph.server.data.DataManager;
import net.melaircraft.seraph.server.exceptions.RestException;
import net.melaircraft.seraph.server.rest.endpoints.Data;
import net.melaircraft.seraph.server.rest.endpoints.Status;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class RestManager {
    private final RestServer restServer;

    public RestManager(RestConfiguration restConfiguration, DataManager dataManager) {
        restServer = new RestServer(restConfiguration.getHost(), restConfiguration.getPort(), restConfiguration.isAllowWrite(), dataManager);

        try {
            restServer.start();
        } catch (IOException e) {
            throw new RestException("Could not start REST server!", e);
        }
    }

    public void stop() {
        if (restServer != null) {
            restServer.stop();
        }
    }

    class RestServer extends Application {
        private final Set<Object> resources = new HashSet<>();

        private final URI baseURI;
        private HttpServer server = null;

        public RestServer(String host, int port, boolean allowWrite, DataManager dataManager) {
            this.baseURI = UriBuilder.fromUri("http://" + host + "/").port(port).build();
            this.resources.add(new Status());
            this.resources.add(new Data(dataManager, allowWrite));
        }

        public void start() throws IOException {
            server = HttpServer.create(new InetSocketAddress(baseURI.getPort()), 0);

            HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(this, HttpHandler.class);
            server.createContext(baseURI.getPath(), handler);

            server.start();
        }

        public void stop() {
            server.stop(0);
            server = null;
        }

        @Override
        public Set<Object> getSingletons() {
            return resources;
        }
    }
}
