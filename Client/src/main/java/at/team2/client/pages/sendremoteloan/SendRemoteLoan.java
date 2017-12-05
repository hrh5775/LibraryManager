package at.team2.client.pages.sendremoteloan;

import javax.lang.model.type.NullType;

import at.team2.client.helper.RmiHelper;
import at.team2.client.pages.BasePage;
import at.team2.common.interfaces.MainRemoteObjectInf;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SendRemoteLoan extends BasePage<Void,NullType,NullType,NullType> {
    @FXML
    private TextField _messageTextfield;
    @FXML
    private Button _sendButton;

    @Override
    public void initialize() {
        Parent parent = loadView(SendRemoteLoan.class.getResource("send_remote_loan.fxml"));
        setCenter(parent);
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
            MainRemoteObjectInf remoteObject = RmiHelper.getSession();
            remoteObject.getMessageRemoteObject().sendMessageForInterLibraryLoan(_messageTextfield.getText());
            showSuccessMessage("The message was successfully sent", "");
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }
    }
}