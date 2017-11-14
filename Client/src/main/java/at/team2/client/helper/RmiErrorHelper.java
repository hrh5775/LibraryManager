package at.team2.client.helper;

import javafx.scene.layout.Pane;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;

public class RmiErrorHelper {
    public static void showRmiErrorMessage(Exception e, Pane pane) {
        String headerText;
        String contentText;

        if(e instanceof ConnectException) {
            headerText = "Failed to connect to the server";
            contentText = "Please check your network connection and try again.";
        } else if(e instanceof UnmarshalException) {
            headerText = "Internal Error";
            contentText = "Could not convert the data to the specified type.";
        } else if(e instanceof RemoteException) {
            headerText = "Internal Error";
            contentText = e.getMessage();
        } else if(e instanceof NotBoundException) {
            headerText = "Not Bound";
            contentText = e.getMessage();
        } else {
            headerText = "Unspecified Error";
            contentText = "Please inform the developer\n\n" + ExceptionHelper.getStackTrace(e);
        }

        AlertHelper.showErrorMessage(headerText, contentText, pane);
    }
}