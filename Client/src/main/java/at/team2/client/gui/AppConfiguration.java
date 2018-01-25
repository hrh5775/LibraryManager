package at.team2.client.gui;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import at.team2.client.configuration.Configuration;
import at.team2.client.configuration.ConfigurationManager;
import at.team2.client.helper.WebHelper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppConfiguration {
    private final static String _configName = "config";
    private final static String _configDelimiter = "\t";
    private static File _configFile;

    public static File getFile() {
        return new File(getPath().toString(), _configName);
    }

    public static Path getPath() {
        return Paths.get(System.getProperty("user.home"), ".LibraryManagerClient");
    }

    public static Configuration getConfiguration() {
        Configuration _configuration;

        Path configDir = getPath();
        // load the config file
        _configFile = getFile();

        try {
            configDir.toFile().mkdirs();
            _configuration = ConfigurationManager.loadConfiguration(_configFile, "\t");
        } catch (IOException e) {
            // create new default Configuration
            _configuration = new Configuration();

            try {
                ConfigurationManager.saveConfiguration(_configuration, _configDelimiter, _configFile, true);
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("configuration could not be saved");
            }
        }

        // check if the webbrowser instead of this application should be started
        /*if (_configuration.getOpenInWebbrowser()) {
            try {
                WebHelper.openInWebbrowser(new URI(_configuration.getRootURI()));
            } catch (URISyntaxException e) {
            }
        }*/

        return _configuration;
    }

    public static void saveConfiguration(Stage primaryStage, Configuration configuration) {
        // save config
        if (primaryStage != null) {
            configuration.setWidth((int) primaryStage.getWidth());
            configuration.setHeight((int) primaryStage.getHeight());
        }

        try {
            ConfigurationManager.saveConfiguration(configuration, _configDelimiter, _configFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getAppIcon() {
        Image anotherIcon = new Image(AppConfiguration.class.getClassLoader().getResourceAsStream("logo.png"));
        return anotherIcon;
    }
}
