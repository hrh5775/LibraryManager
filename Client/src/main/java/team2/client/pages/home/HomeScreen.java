package team2.client.pages.home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import team2.client.pages.BasePage;
import javax.lang.model.type.NullType;


public class HomeScreen extends BasePage<Void, NullType, NullType, NullType> {
    public HomeScreen() {
    }

    @Override
    public void initialize() {
        Image image1 = new Image("logo.png");
        BackgroundSize bSize = new BackgroundSize(500, 500, false, false, true, false);

        this.setBackground(new Background(new BackgroundImage(image1,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        final ObservableList<String> _data;
        _data = FXCollections.observableArrayList(
                new String("Test1"),
                new String("Test2"));

        Label loginLabel=new Label("Username:");
        loginLabel.setStyle(" -fx-font-size: 20px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        Label usernameLabel = new Label();
        usernameLabel.setStyle("-fx-font: 14 arial;");

        ComboBox<String> usernameCombobox = new ComboBox<String >(_data);
        usernameCombobox.setMinWidth(125);
        usernameCombobox.setStyle("-fx-font: 14 arial;");
        javafx.scene.control.Label labelYear = new javafx.scene.control.Label("Year:");
        labelYear.setMinWidth(125);
        labelYear.setStyle("-fx-font: 14 arial;");
        usernameCombobox.getSelectionModel().select(1);

        Button loginButton = new Button("Login");
        loginButton.setMinWidth(125);
        loginButton.setStyle("-fx-font: 14 arial;");

        Button logoutButton = new Button("Logout");
        logoutButton.setMinWidth(125);
        logoutButton.setStyle("-fx-font: 14 arial;");

        VBox loginBox = new VBox();
        loginBox.setMaxSize(125,100);
        loginBox.getChildren().addAll(loginLabel, usernameCombobox, loginButton);
        loginBox.setSpacing(5);

        setTop(loginBox);
        loginBox.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        logoutButton.setOnAction(event -> {
            loginBox.getChildren().removeAll(logoutButton, usernameLabel);
            loginBox.getChildren().addAll(usernameCombobox, loginButton);
        });

        loginButton.setOnAction(event -> {
            usernameLabel.setText("Logged in: "+usernameCombobox.getSelectionModel().getSelectedItem().toString());
            loginBox.getChildren().removeAll(loginButton, usernameCombobox);
            loginBox.getChildren().addAll(usernameLabel,logoutButton);
        });
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
