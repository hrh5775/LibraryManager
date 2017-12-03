package at.team2.client.entities;

import at.team2.common.interfaces.*;
import java.rmi.RemoteException;

public class MainRemoteObject implements MainRemoteObjectInf {
    private MainRemoteObjectInf _mainRemoteObject;
    private BookRemoteObjectInf _bookRemoteObject;
    private DvdRemoteObjectInf _dvdRemoteObject;
    private CustomerRemoteObjectInf _customerRemoteObject;
    private LoanRemoteObjectInf _loanRemoteObject;
    private ReservationRemoteObjectInf _reservationRemoteObject;
    private MediaMemberRemoteObjectInf _mediaMemberRemoteObject;
    private AccountRemoteObjectInf _accountRemoteObject;
    private MessageRemoteObjectInf _messageRemoteObject;

    public MainRemoteObject(MainRemoteObjectInf mainRemoteObject,
                            BookRemoteObjectInf bookRemoteObject,
                            DvdRemoteObjectInf dvdRemoteObject,
                            CustomerRemoteObjectInf customerRemoteObject,
                            LoanRemoteObjectInf loanRemoteObject,
                            ReservationRemoteObjectInf reservationRemoteObject,
                            MediaMemberRemoteObjectInf mediaMemberRemoteObject,
                            AccountRemoteObjectInf accountRemoteObject,
                            MessageRemoteObjectInf messageRemoteObject) {
        if(mainRemoteObject == null) {
            throw new IllegalArgumentException("mainRemoteObject is null");
        }

        if(bookRemoteObject == null) {
            throw new IllegalArgumentException("bookRemoteObject is null");
        }

        if(dvdRemoteObject == null) {
            throw new IllegalArgumentException("dvdRemoteObject is null");
        }

        if(customerRemoteObject == null) {
            throw new IllegalArgumentException("customerRemoteObject is null");
        }

        if(loanRemoteObject == null) {
            throw new IllegalArgumentException("loanRemoteObject is null");
        }

        if(reservationRemoteObject == null) {
            throw new IllegalArgumentException("reservationRemoteObject is null");
        }

        if(mediaMemberRemoteObject == null) {
            throw new IllegalArgumentException("mediaMemberRemoteObject is null");
        }

        if(accountRemoteObject == null) {
            throw new IllegalArgumentException("accountRemoteObject is null");
        }

        if(messageRemoteObject == null) {
            throw new IllegalArgumentException("messageRemoteObject is null");
        }

        _mainRemoteObject = mainRemoteObject;
        _bookRemoteObject = bookRemoteObject;
        _dvdRemoteObject = dvdRemoteObject;
        _customerRemoteObject = customerRemoteObject;
        _loanRemoteObject = loanRemoteObject;
        _reservationRemoteObject = reservationRemoteObject;
        _mediaMemberRemoteObject = mediaMemberRemoteObject;
        _accountRemoteObject = accountRemoteObject;
        _messageRemoteObject = messageRemoteObject;
    }

    @Override
    public int getVersion() throws RemoteException {
        return _mainRemoteObject.getVersion();
    }

    @Override
    public BookRemoteObjectInf getBookRemoteObject() throws RemoteException {
        return _bookRemoteObject;
    }

    @Override
    public DvdRemoteObjectInf getDvdRemoteObject() throws RemoteException {
        return _dvdRemoteObject;
    }

    @Override
    public CustomerRemoteObjectInf getCustomerRemoteObject() throws RemoteException {
        return _customerRemoteObject;
    }

    @Override
    public LoanRemoteObjectInf getLoanRemoteObject() throws RemoteException {
        return _loanRemoteObject;
    }

    @Override
    public ReservationRemoteObjectInf getReservationRemoteObject() throws RemoteException {
        return _reservationRemoteObject;
    }

    @Override
    public MediaMemberRemoteObjectInf getMediaMemberRemoteObject() throws RemoteException {
        return _mediaMemberRemoteObject;
    }

    @Override
    public AccountRemoteObjectInf getAccountRemoteObject() throws RemoteException {
        return _accountRemoteObject;
    }

    @Override
    public MessageRemoteObjectInf getMessageRemoteObject() throws RemoteException {
        return _messageRemoteObject;
    }
}
