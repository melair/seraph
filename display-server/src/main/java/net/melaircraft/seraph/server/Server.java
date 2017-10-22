package net.melaircraft.seraph.server;

import net.melaircraft.seraph.server.config.ConfigurationManager;
import net.melaircraft.seraph.server.config.model.DisplayConfiguration;
import net.melaircraft.seraph.server.config.model.RestConfiguration;
import net.melaircraft.seraph.server.data.DataManager;
import net.melaircraft.seraph.server.display.DisplayManager;
import net.melaircraft.seraph.server.rest.RestManager;
import net.melaircraft.seraph.server.source.SourceManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Server {
    public static void main(String[] args) {
        /* Configuration, loading and checking. */
        ConfigurationManager configurationManager = new ConfigurationManager();
        DisplayConfiguration displayConfiguration = configurationManager.getDisplayConfiguration();
        RestConfiguration restConfiguration = configurationManager.getRestConfiguration();

        /* Scheduled background tasks. */
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        /* Display manager for driving Seraph display. */
        DisplayManager displayManager = new DisplayManager(displayConfiguration, scheduler);

        /* Data Manager. */
        DataManager dataManager = new DataManager();

        /* Source Manager. */
        SourceManager sourceManager = new SourceManager(dataManager, scheduler);

        /* Rest Server. */
        if (restConfiguration.isEnabled()) {
            RestManager restManager = new RestManager(restConfiguration, dataManager);
        }
    }
}

