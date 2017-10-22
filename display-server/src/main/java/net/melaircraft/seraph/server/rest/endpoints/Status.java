package net.melaircraft.seraph.server.rest.endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/status")
public class Status {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("application/json")
    public Response getRoot() {
        JsonObject status = new JsonObject();
        status.addProperty("status", "OK");
        return Response.ok(gson.toJson(status), "application/json").build();
    }
}
