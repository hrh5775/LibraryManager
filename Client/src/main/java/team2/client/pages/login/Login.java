package team2.client.pages.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import java.io.IOException;
import team2.client.pages.BasePage;

public class Login extends BasePage<Void, NullType, NullType, NullType> {
    public Login() {
    }

    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent parent = loader.load();
            this.setCenter(parent);
        } catch (IOException ex) {
            System.out.print(ex);
        }
    }

    @Override
    public void load() {
    }

    @Override
    public void update() {
    }

    @Override
    public void exit() {
    }

    @Override
    public void dispose() {
    }
}
