package at.team2.server.remote.rmi;

import at.team2.common.interfaces.rmi.*;
import at.team2.server.common.MainBase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainRemoteObject extends UnicastRemoteObject implements MainRemoteObjectInf {
    private MainBase _mainBase;

    public MainRemoteObject() throws RemoteException {
        _mainBase = new MainBase();
    }

    @Override
    public int getVersion() {
        return _mainBase.doGetVersion();
    }

    @Override
    public BookRemoteObjectInf getBookRemoteObject() throws RemoteException {
        return new BookRemoteObject();
    }

    @Override
    public DvdRemoteObjectInf getDvdRemoteObject() throws RemoteException {
        return new DvdRemoteObject();
    }


    @Override
    public CustomerRemoteObjectInf getCustomerRemoteObject() throws RemoteException {
        return new CustomerRemoteObject();
    }

    @Override
    public LoanRemoteObjectInf getLoanRemoteObject() throws RemoteException {
        return new LoanRemoteObject();
    }

    @Override
    public ReservationRemoteObjectInf getReservationRemoteObject() throws RemoteException {
        return new ReservationRemoteObject();
    }

    @Override
    public MediaMemberRemoteObjectInf getMediaMemberRemoteObject() throws RemoteException {
        return new MediaMemberRemoteObject();
    }

    @Override
    public AccountRemoteObjectInf getAccountRemoteObject() throws RemoteException {
        return new AccountRemoteObject();
    }

    @Override
    public MessageRemoteObjectInf getMessageRemoteObject() throws RemoteException {
        return new MessageRemoteObject();
    }
}