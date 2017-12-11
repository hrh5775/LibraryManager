package at.team2.client.helper;

import at.team2.common.interfaces.ejb.MainRemote;

public class EjbHelper {
    private static MainRemote _mainRemoteObject;

    public static MainRemote getSession() {
        return _mainRemoteObject;
    }

    public static void setSession(MainRemote mainRemote) {
        _mainRemoteObject = mainRemote;
    }
}