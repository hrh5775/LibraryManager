package at.team2.client.pages.showremoteloan;

import at.team2.client.common.AccountManager;
import at.team2.client.helper.RmiHelper;
import at.team2.client.pages.BasePage;
import at.team2.common.interfaces.MainRemoteObjectInf;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javax.lang.model.type.NullType;

public class ShowRemoteLoan extends BasePage<Void,NullType,NullType,NullType> {
    @FXML
    private TextArea _messageTextfield;
    @FXML
    private Button _sendButton;

    @Override
    public void initialize() {
        Parent parent = loadView(ShowRemoteLoan.class.getResource("show_remote_loan.fxml"));
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
            String message = remoteObject.getMessageRemoteObject().receiveMessageForInterLibraryLoan(AccountManager.getInstance().getAccount());

            if(message != null) {
                _messageTextfield.textProperty().setValue(message);
            } else {
                showSuccessMessage("There are no more messages available", "");
                _messageTextfield.textProperty().setValue("");
            }
        } catch (Exception e) {
            showRmiErrorMessage(e);
        }
    }
}