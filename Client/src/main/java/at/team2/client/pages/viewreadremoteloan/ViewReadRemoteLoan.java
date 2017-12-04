package at.team2.client.pages.viewreadremoteloan;

import javax.lang.model.type.NullType;

import at.team2.client.helper.RmiHelper;
import at.team2.client.pages.BasePage;
import at.team2.common.interfaces.MainRemoteObjectInf;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ViewReadRemoteLoan extends BasePage<Void,NullType,NullType,NullType> {
    @FXML
    private TextField _messageTextfield;
    @FXML
    private Button _sendButton;

    @Override
    public void initialize() {
        Parent parent = loadView(ViewReadRemoteLoan.class.getResource("view_read_remote_loan.fxml"));
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
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}