package at.team2.client.controls.sidebar;

import at.team2.client.helper.ImageUtilHelper;
import javafx.geometry.Insets;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.util.Collection;

public class MenuSection extends TitledPane {
    private VBox _vBox;
    private ToggleGroup _toggleGroup;

    public MenuSection(String title, String graphicPath, ToggleGroup toggleGroup) {
        setText(title);
        Image image = ImageUtilHelper.getImage(graphicPath);
        ImageView imageView = new ImageView(image);
        imageView.setSmooth(true);
        setGraphic(imageView);
        setWrapText(false);
        //setTextOverrun(OverrunStyle.CLIP);
        setEllipsisString(" ");
        setPadding(new Insets(0, 0, 0, 0));

        // initialize with predefined settings
        _vBox = new VBox(5);
        _vBox.setPadding(new Insets(0, 0, 0, 0));
        _vBox.setStyle("-fx-background-color: #e0e0d1");
        _vBox.setMinWidth(0);
        setContent(_vBox);
        setStyle(" -fx-font-size: 1em;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow(gaussian, rgba(255,255,255,0.5), 0, 0, 0, 1);");

        _toggleGroup = toggleGroup;
    }

    public MenuSection(String title, String graphicPath, ToggleGroup toggleGroup, Collection<MenuSectionItem> menuSectionItems) {
        this(title, graphicPath, toggleGroup);

        addAll(menuSectionItems);
    }

    public boolean add(MenuSectionItem menuSectionItem) {
        boolean result = true;

        if (menuSectionItem != null) {
            menuSectionItem.setToggleGroup(_toggleGroup);
            _vBox.getChildren().add(menuSectionItem);
        } else {
            result = false;
        }

        return result;
    }

    public boolean addAll(Collection<MenuSectionItem> menuSectionItems) {
        boolean result = true;

        if (menuSectionItems != null) {
            for (MenuSectionItem item : menuSectionItems) {
                if (!add(item)) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }

        return result;
    }
}
