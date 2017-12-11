package at.team2.client.pages.sendremoteloan;

import javax.lang.model.type.NullType;

import at.team2.client.common.AccountManager;
import at.team2.client.entities.session.SessionWrapperObject;
import at.team2.client.helper.SessionHelper;
import at.team2.client.pages.BasePage;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class SendRemoteLoan extends BasePage<Void,NullType,NullType,NullType> {
    @FXML
    private TextArea _messageTextfield;
    @FXML
    private Button _sendButton;

    @Override
    public void initialize() {
        Parent parent = loadView(SendRemoteLoan.class.getResource("send_remote_loan.fxml"));
        setCenter(parent);

        _sendButton.disableProperty().bind(_messageTextfield.textProperty().isEmpty());
    }

    @Override
    public void initializeView() {
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
    private void send() {
        try {
            SessionWrapperObject remoteObject = SessionHelper.getSession();
            remoteObject.getMessageRemoteObject().sendMessageForInterLibraryLoan(_messageTextfield.textProperty().getValue(), AccountManager.getInstance().getAccount());
            _messageTextfield.textProperty().setValue("");
            showSuccessMessage("The message was successfully sent", "");
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }
    }
}