package at.team2.client.helper;

import at.team2.client.entities.MainRemoteObject;
import at.team2.common.interfaces.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiHelper {
    private static MainRemoteObjectInf _mainRemoteObject;

    public static MainRemoteObjectInf getSession() throws RemoteException, NotBoundException {
        if(_mainRemoteObject != null) {
            return _mainRemoteObject;
        } else {
            return at.team2.common.helper.RmiHelper.getSession(at.team2.common.helper.RmiHelper.getRegistry());
        }
    }

    public static void setEJBSession(MainRemoteObjectInf mainRemoteObject,
                                     BookRemoteObjectInf bookRemoteObject,
                                     DvdRemoteObjectInf dvdRemoteObject,
                                     CustomerRemoteObjectInf customerRemoteObject,
                                     LoanRemoteObjectInf loanRemoteObject,
                                     ReservationRemoteObjectInf reservationRemoteObject,
                                     MediaMemberRemoteObjectInf mediaMemberRemoteObject,
                                     AccountRemoteObjectInf accountRemoteObject,
                                     MessageRemoteObjectInf messageRemoteObject) {
        _mainRemoteObject = new MainRemoteObject(mainRemoteObject,
                bookRemoteObject,
                dvdRemoteObject,
                customerRemoteObject,
                loanRemoteObject,
                reservationRemoteObject,
                mediaMemberRemoteObject,
                accountRemoteObject,
                messageRemoteObject);
    }
}