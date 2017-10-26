package at.team2.client.pages.home;

import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;

public class HomeScreen extends BasePage<Void, NullType, NullType, NullType> {
    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(HomeScreen.class.getResource("home_screen.fxml"));
        setCenter(parent);
    }

    @Override
    public void load() {
    }

    @Override
    public void reload() {

    }

    @Override
    public void exit() {
    }

    @Override
    public void dispose() {
    }
}