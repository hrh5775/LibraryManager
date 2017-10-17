package team2.client.pages.home;

import team2.client.pages.BasePage;
import team2.client.pages.login.Login;
import javax.lang.model.type.NullType;


public class HomeScreen extends BasePage<Void, NullType, NullType, NullType> {
    public HomeScreen() {
    }

    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        /*Image image = new Image("logo.png");
        BackgroundSize bSize = new BackgroundSize(500, 500, false, false, true, false);

        this.setBackground(new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));*/

        Login login = new Login();
        login.initializeView();
        this.setTop(login);
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
