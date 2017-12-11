package at.team2.client.helper;

import at.team2.common.interfaces.rmi.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiHelper {
    public static MainRemoteObjectInf getSession() throws RemoteException, NotBoundException {
        return at.team2.common.helper.RmiHelper.getSession(at.team2.common.helper.RmiHelper.getRegistry());
    }
}