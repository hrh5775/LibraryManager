package at.team2.client.helper;

import at.team2.client.entities.session.SessionWrapperObject;
import at.team2.common.interfaces.ejb.MainRemote;
import at.team2.common.interfaces.rmi.MainRemoteObjectInf;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SessionHelper {
    private static MainRemoteObjectInf _mainRemoteObject;
    private static MainRemote _mainRemote;

    public static SessionWrapperObject getSession() throws RemoteException, NotBoundException {
        if(_mainRemote == null) {
            _mainRemote = EjbHelper.getSession();
        }

        if(_mainRemote != null) {
            return new SessionWrapperObject(_mainRemote);
        } else {
            if(_mainRemote == null) {
                _mainRemoteObject = RmiHelper.getSession();
            }

            return new SessionWrapperObject(_mainRemoteObject);
        }
    }
}