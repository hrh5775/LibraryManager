package at.team2.client.controls.sidebar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

public class MenuSectionItem extends ToggleButton {
    public MenuSectionItem(String title) {
        setText(title);
        setStyle(" -fx-font-size: 0.6em;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow(gaussian, rgba(255,255,255,0.5), 0, 0, 0, 1);");
        // initialize with predefined settings
        setMinWidth(0);
        setMaxWidth(Double.MAX_VALUE);
    }

    public MenuSectionItem(String title, EventHandler<ActionEvent> onclick) {
        this(title);

        setOnAction(onclick);
    }
}