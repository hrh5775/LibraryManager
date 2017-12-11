package at.team2.client.pages.login;

import at.team2.client.common.AccountManager;
import at.team2.client.controls.loadingindicator.LoadingIndicator;
import at.team2.client.entities.session.SessionWrapperObject;
import at.team2.client.gui.Navigation;
import at.team2.client.helper.SessionHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javax.lang.model.type.NullType;
import at.team2.client.pages.BasePage;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;

public class Login extends BasePage<Void, NullType, NullType, NullType> {
    @FXML
    private LoadingIndicator _loadingIndicator;
    @FXML
    private Pane _mainPanel;
    @FXML
    private BooleanProperty _isLoading;
    @FXML
    private TextInputControl _userNameInput;
    @FXML
    private TextInputControl _passwordInput;
    @FXML
    private SimpleStringProperty _userName;
    @FXML
    private SimpleStringProperty _password;
    @FXML
    private Button _loginButton;

    private Thread _loginTask;

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

        _userNameInput.textProperty().bindBidirectional(_userName);
        _passwordInput.textProperty().bindBidirectional(_password);

        _loginButton.disableProperty().bind(_password.isEmpty().or(_password.isEmpty()));
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
        if(_loginTask == null && _userName.getValue() != null && _password.getValue() != null &&
                !_userName.getValue().isEmpty() && !_password.getValue().isEmpty()) {
            _isLoading.setValue(true);

            _loginTask = startBackgroundTask(() -> {
                try {
                    SessionWrapperObject remoteObject = SessionHelper.getSession();
                    AccountDetailedDto entity = remoteObject.getAccountRemoteObject().login(_userName.getValue(), _password.getValue());

                    if (entity != null) {
                        _userName.setValue("");
                        _password.setValue("");

                        AccountManager.getInstance().setAccount(entity);
                        Platform.runLater(() -> Navigation.getInstance().loadStartPage());
                    } else {
                        Platform.runLater(() -> showErrorMessage("Credentials are incorrect", ""));
                    }
                } catch (Exception e) {
                    Platform.runLater(() -> showRmiErrorMessage(e));
                } finally {
                    _loginTask = null;
                    Platform.runLater(() -> _isLoading.setValue(false));
                }
            });
        }
    }

    @FXML
    private void cancel() {
        try {
            stopTask(_loginTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            _isLoading.setValue(false);
        }
    }
}