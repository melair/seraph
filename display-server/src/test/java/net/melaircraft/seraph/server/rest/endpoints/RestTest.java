package net.melaircraft.seraph.server.rest.endpoints;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.RestAssured.given;

public abstract class RestTest extends Application {
    protected static AtomicInteger counter = new AtomicInteger();
    protected static final int port = 8000 + counter.getAndIncrement();
    protected RequestSpecification request;
    private HttpServer server;

    @Before
    public void setUp() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(this, HttpHandler.class);
        server.createContext("/", handler);

        server.start();

        request = given().port(port);
    }

    @After
    public void tearDown() {
        server.stop(0);
    }

    @Override
    public abstract Set<Object> getSingletons();
}