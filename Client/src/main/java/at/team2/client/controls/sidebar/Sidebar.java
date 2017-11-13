package at.team2.client.controls.sidebar;

import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import java.util.Collection;

public class Sidebar extends ScrollPane {
    private Accordion _accordion;

    public Sidebar() {
        _accordion = new Accordion();
        _accordion.setStyle("-fx-background-color: #e0e0d1");
        _accordion.prefWidthProperty().bind(widthProperty());
        _accordion.minWidthProperty().bind(minWidthProperty());
        _accordion.maxWidthProperty().bind(maxWidthProperty());

        setContent(_accordion);
    }

    public boolean add(MenuSection menuSection) {
        boolean result = true;

        if (menuSection != null) {
            _accordion.getPanes().add(menuSection);
        } else {
            result = false;
        }

        return result;
    }

    public boolean addAll(Collection<MenuSection> menuSections) {
        boolean result = true;

        if (menuSections != null) {
            for (MenuSection item : menuSections) {
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

    public void clear() {
        _accordion.getPanes().clear();
    }
}
