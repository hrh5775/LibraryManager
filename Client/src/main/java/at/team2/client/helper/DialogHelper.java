package at.team2.client.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

import java.util.Optional;

public class DialogHelper {
    public static void showYesNoDialog(String title, String headerText, String contentText, Pane pane, Runnable onYesAction, Runnable onNoAction) {
        ButtonType yesButtonType = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButtonType = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                contentText,
                yesButtonType,
                noButtonType);

        alert.setHeaderText(headerText);
        alert.setTitle(title);

        pane.setDisable(true);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButtonType) {
            if(result.get() == yesButtonType) {
                if(onYesAction != null) {
                    new Thread(onYesAction).start();
                }
            } else if (result.get() == noButtonType){
                if(onNoAction != null) {
                    new Thread(onNoAction).start();
                }
            }
        }

        pane.setDisable(false);
    }
}