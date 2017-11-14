package at.team2.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MainRemoteObjectInf extends Remote {
    public int getVersion() throws RemoteException;
    public BookRemoteObjectInf getBookRemoteObject() throws RemoteException;
    public DvdRemoteObjectInf getDvdRemoteObject() throws RemoteException;
    public CustomerRemoteObjectInf getCustomerRemoteObject() throws RemoteException;
    public LoanRemoteObjectInf getLoanRemoteObject() throws RemoteException;
    public ReservationRemoteObjectInf getReservationRemoteObject() throws RemoteException;
    public MediaMemberRemoteObjectInf getMediaMemberRemoteObject() throws RemoteException;
    public AccountRemoteObjectInf getAccountRemoteObject() throws RemoteException;
}