package net.melaircraft.seraph.server.rest.endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import net.melaircraft.seraph.server.data.DataManager;
import net.melaircraft.seraph.server.data.Entry;
import net.melaircraft.seraph.server.data.value.BooleanValue;
import net.melaircraft.seraph.server.data.value.NumberValue;
import net.melaircraft.seraph.server.data.value.StringValue;
import net.melaircraft.seraph.server.data.value.Value;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/data")
public class Data {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final JsonParser jsonParser = new JsonParser();
    private final DataManager dataManager;
    private final boolean allowWrite;

    public Data(DataManager dataManager, boolean allowWrite) {
        this.dataManager = dataManager;
        this.allowWrite = allowWrite;
    }

    @GET
    @Produces("application/json")
    public Response list() {
        JsonObject list = new JsonObject();

        dataManager.getEntryNames().stream().sorted().forEach((name) -> {
            JsonObject object = new JsonObject();

            Entry entry = dataManager.get(name);

            entry.getMap().forEach((s, value) -> addToJson(object, s, value));

            list.add(name, object);
        });

        return Response.status(Response.Status.OK).entity(gson.toJson(list)).type("application/json").build();
    }

    @PUT
    @Path("/{name}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response put(@PathParam("name") String name, String body) {
        if (!allowWrite) {
            return generateError(Response.Status.METHOD_NOT_ALLOWED,"Writes are not enabled.");
        }

        JsonObject jsonObject;

        try {
            jsonObject = jsonParser.parse(body).getAsJsonObject();
        } catch (JsonIOException | IllegalStateException e) {
            return generateError(Response.Status.BAD_REQUEST, "JSON provided is not valid.");
        }

        Map<String, Value> values = new HashMap<>();

        for (String key : jsonObject.keySet()) {
            JsonElement element = jsonObject.get(key);

            if (!element.isJsonPrimitive()) {
                return generateError(Response.Status.BAD_REQUEST, "Data must only contain primitive values.");
            }

            JsonPrimitive primitive = (JsonPrimitive) element;

            if (primitive.isString()) {
                values.put(key, new StringValue(primitive.getAsString()));
            } else if (primitive.isBoolean()) {
                values.put(key, new BooleanValue(primitive.getAsBoolean()));
            } else if (primitive.isNumber()) {
                values.put(key, new NumberValue(primitive.getAsNumber().doubleValue()));
            }
        }

        Entry entry = dataManager.get(name);
        values.forEach(entry::add);

        return generateStatus(Response.Status.OK, "Data accepted.");
    }

    private Response generateError(Response.Status responseStatus, String messageText) {
        JsonObject message = new JsonObject();
        message.addProperty("message", messageText);
        JsonObject status = new JsonObject();
        status.add("error", message);
        return Response.status(responseStatus).entity(gson.toJson(status)).type("application/json").build();
    }

    private Response generateStatus(Response.Status responseStatus, String statusText) {
        JsonObject message = new JsonObject();
        message.addProperty("status", statusText);
        return Response.status(responseStatus).entity(gson.toJson(message)).type("application/json").build();
    }

    private void addToJson(JsonObject jsonObject, String key, Value value) {
        Object raw = value.getRaw();

        if (raw instanceof String) {
            jsonObject.addProperty(key, (String) raw);
        } else if (raw instanceof Number) {
            jsonObject.addProperty(key, (Number) raw);
        } else if (raw instanceof Boolean) {
            jsonObject.addProperty(key, (Boolean) raw);
        }
    }
}
