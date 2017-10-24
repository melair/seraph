package net.melaircraft.seraph.server.rest.endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.melaircraft.seraph.server.data.DataManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/data")
public class Data {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final DataManager dataManager;
    private final boolean allowWrite;

    public Data(DataManager dataManager, boolean allowWrite) {
        this.dataManager = dataManager;
        this.allowWrite = allowWrite;
    }

    @GET
    @Produces("application/json")
    public Response list() {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @PUT
    @Path("/{name}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response put(@PathParam("name") String name, String body) {
        if (!allowWrite) {
            JsonObject message = new JsonObject();
            message.addProperty("message", "Writes are not enabled.");
            JsonObject status = new JsonObject();
            status.add("error", message);
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(gson.toJson(status)).type("application/json").build();
        }

        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
