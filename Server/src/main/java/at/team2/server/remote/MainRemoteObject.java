package at.team2.server.remote;

import at.team2.common.configuration.ConnectionInfo;
import at.team2.common.interfaces.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainRemoteObject extends UnicastRemoteObject implements MainRemoteObjectInf {
    private BookRemoteObjectInf _bookRemoteObjectInf;
    private DvdRemoteObjectInf _dvdRemoteObjectInf;
    private CustomerRemoteObjectInf _customerRemoteObjectInf;
    private LoanRemoteObjectInf _loanRemoteObjectInf;
    private MediaMemberRemoteObjectInf _mediaMemberRemoteObjectInf;
    private AccountRemoteObjectInf _accountRemoteObjectInf;

    public MainRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public int getVersion() {
        return ConnectionInfo.version;
    }

    @Override
    public BookRemoteObjectInf getBookRemoteObject() throws RemoteException {
        if(_bookRemoteObjectInf == null) {
            _bookRemoteObjectInf = new BookRemoteObject();
        }

        return _bookRemoteObjectInf;
    }

    @Override
    public DvdRemoteObjectInf getDvdRemoteObject() throws RemoteException {
        if(_dvdRemoteObjectInf == null) {
            _dvdRemoteObjectInf = new DvdRemoteObject();
        }

        return _dvdRemoteObjectInf;
    }


    @Override
    public CustomerRemoteObjectInf getCustomerRemoteObject() throws RemoteException {
        if(_customerRemoteObjectInf == null) {
            _customerRemoteObjectInf = new CustomerRemoteObject();
        }

        return _customerRemoteObjectInf;
    }

    @Override
    public LoanRemoteObjectInf getLoanRemoteObject() throws RemoteException {
        if(_loanRemoteObjectInf == null) {
            _loanRemoteObjectInf = new LoanRemoteObject();
        }

        return _loanRemoteObjectInf;
    }

    @Override
    public MediaMemberRemoteObjectInf getMediaMemberRemoteObject() throws RemoteException {
        if(_mediaMemberRemoteObjectInf == null) {
            _mediaMemberRemoteObjectInf = new MediaMemberRemoteObject();
        }

        return _mediaMemberRemoteObjectInf;
    }

    @Override
    public AccountRemoteObjectInf getAccountRemoteObject() throws RemoteException {
        if(_accountRemoteObjectInf == null) {
            _accountRemoteObjectInf = new AccountRemoteObject();
        }

        return _accountRemoteObjectInf;
    }
}