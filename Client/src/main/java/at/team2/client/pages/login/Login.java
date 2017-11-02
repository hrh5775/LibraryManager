package at.team2.client.pages.login;

import at.team2.client.controls.loadingindicator.LoadingIndicator;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;
import javafx.scene.layout.Pane;

public class Login extends BasePage<Void, NullType, NullType, NullType> {
    @FXML
    private LoadingIndicator _loadingIndicator;
    @FXML
    private Pane _mainPanel;
    @FXML
    private BooleanProperty _isLoading;

    @Override
    public void initialize() {
    }

    @Override
    public void initializeView() {
        Parent parent = loadView(Login.class.getResource("login.fxml"));
        setCenter(parent);

        _isLoading.setValue(false);
        _loadingIndicator.visibleProperty().bind(_isLoading);
        _mainPanel.visibleProperty().bind(_isLoading.not());
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

    @FXML
    private void login() {
        _isLoading.setValue(true);
    }
}