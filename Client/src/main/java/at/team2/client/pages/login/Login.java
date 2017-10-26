package at.team2.client.pages.login;

import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;

public class Login extends BasePage<Void, NullType, NullType, NullType> {
    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(Login.class.getResource("login.fxml"));
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