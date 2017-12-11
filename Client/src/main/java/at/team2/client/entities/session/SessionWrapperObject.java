package at.team2.client.entities.session;

import at.team2.common.interfaces.ejb.*;
import at.team2.common.interfaces.rmi.*;

import java.rmi.RemoteException;

public class SessionWrapperObject {
    private MainRemoteObjectInf _mainRemoteObject;
    private MainRemote _mainRemote;

    public SessionWrapperObject(MainRemoteObjectInf mainRemoteObject) {
        _mainRemoteObject = mainRemoteObject;
    }

    public SessionWrapperObject(MainRemote mainRemote) {
        _mainRemote = mainRemote;
    }

    public int getVersion() throws RemoteException {
        if(_mainRemote != null) {
            return _mainRemote.getVersion();
        } else {
            return _mainRemoteObject.getVersion();
        }
    }

    public BookRemoteObjectInf getBookRemoteObject() throws RemoteException {
        if(_mainRemote != null) {
            return new BookRemoteWrapper(_mainRemote.getBookRemoteObject());
        } else {
            return _mainRemoteObject.getBookRemoteObject();
        }
    }

    public DvdRemoteObjectInf getDvdRemoteObject() throws RemoteException {
        if(_mainRemote != null) {
            return new DvdRemoteWrapper(_mainRemote.getDvdRemoteObject());
        } else {
            return _mainRemoteObject.getDvdRemoteObject();
        }
    }

    public CustomerRemoteObjectInf getCustomerRemoteObject() throws RemoteException {
        if(_mainRemote != null) {
            return new CustomerRemoteWrapper(_mainRemote.getCustomerRemoteObject());
        } else {
            return _mainRemoteObject.getCustomerRemoteObject();
        }
    }

    public LoanRemoteObjectInf getLoanRemoteObject() throws RemoteException {
        if(_mainRemote != null) {
            return new LoanRemoteWrapper(_mainRemote.getLoanRemoteObject());
        } else {
            return _mainRemoteObject.getLoanRemoteObject();
        }
    }

    public ReservationRemoteObjectInf getReservationRemoteObject() throws RemoteException {
        if(_mainRemote != null) {
            return new ReservationRemoteWrapper(_mainRemote.getReservationRemoteObject());
        } else {
            return _mainRemoteObject.getReservationRemoteObject();
        }
    }

    public MediaMemberRemoteObjectInf getMediaMemberRemoteObject() throws RemoteException {
        if(_mainRemote != null) {
            return new MediaMemberRemoteWrapper(_mainRemote.getMediaMemberRemoteObject());
        } else {
            return _mainRemoteObject.getMediaMemberRemoteObject();
        }
    }

    public AccountRemoteObjectInf getAccountRemoteObject() throws RemoteException {
        if(_mainRemote != null) {
            return new AccountRemoteWrapper(_mainRemote.getAccountRemoteObject());
        } else {
            return _mainRemoteObject.getAccountRemoteObject();
        }
    }

    public MessageRemoteObjectInf getMessageRemoteObject() throws RemoteException {
        if(_mainRemote != null) {
            return new MessageRemoteWrapper(_mainRemote.getMessageRemoteObject());
        } else {
            return _mainRemoteObject.getMessageRemoteObject();
        }
    }
}