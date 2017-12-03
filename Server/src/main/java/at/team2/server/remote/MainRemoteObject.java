package at.team2.server.remote;

import at.team2.common.configuration.ConnectionInfo;
import at.team2.common.interfaces.*;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Stateless
@Remote(at.team2.common.interfaces.MainRemoteObjectInf.class)
public class MainRemoteObject extends UnicastRemoteObject implements MainRemoteObjectInf {
    public MainRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public int getVersion() {
        return ConnectionInfo.version;
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
}