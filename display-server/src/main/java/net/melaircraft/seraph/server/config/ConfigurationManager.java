package net.melaircraft.seraph.server.config;

import com.google.gson.Gson;
import net.melaircraft.seraph.server.config.model.DisplayConfiguration;
import net.melaircraft.seraph.server.config.model.RestConfiguration;
import net.melaircraft.seraph.server.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigurationManager {
    private final Gson gson = new Gson();
    private DisplayConfiguration displayConfiguration = null;
    private RestConfiguration restConfiguration = null;

    public DisplayConfiguration getDisplayConfiguration() throws InvalidConfigurationException {
        if (displayConfiguration == null) {
            displayConfiguration = loadConfiguration("display.json", DisplayConfiguration.class);
        }

        return displayConfiguration;
    }

    public RestConfiguration getRestConfiguration() throws InvalidConfigurationException {
        if (restConfiguration == null) {
            restConfiguration = loadConfiguration("rest.json", RestConfiguration.class);
        }

        return restConfiguration;
    }

    private <T> T loadConfiguration(String filename, Class<T> classOfT) throws InvalidConfigurationException {
        String locationPrefix = System.getProperty("config.dir", "./");
        String configFile = locationPrefix + filename;

        File file = new File(configFile);

        try (InputStream inputStream = new FileInputStream(file)) {
            return gson.fromJson(new InputStreamReader(inputStream), classOfT);
        } catch (IOException e) {
            throw new InvalidConfigurationException("Could not load: " + configFile, e);
        }
    }
}
