package at.team2.client.gui;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import at.team2.client.configuration.Configuration;

public class Common {
    public static void close() {
        Platform.exit();
        System.exit(0);
    }

    public static void closeApp(Stage primaryStage, Configuration configuration) {
        AppConfiguration.saveConfiguration(primaryStage, configuration);
        close();
        System.exit(0);
    }

    public static void closeAppWithWarning(Event event, Stage primaryStage, Configuration _configuration) {
        boolean close = true;

        if (_configuration.getShowCloseWarning()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close");
            alert.setHeaderText("Close");
            alert.setContentText("Do you want to close the app?");

            ButtonType result = alert.showAndWait().get();

            if (result == ButtonType.OK) {
                close = true;
            } else {
                close = false;
            }
        }

        if (close) {
            Common.closeApp(primaryStage, _configuration);
        } else {
            if (event != null) {
                event.consume();
            }
        }
    }
}
