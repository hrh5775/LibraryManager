package at.team2.server.remote.ejb;

import at.team2.common.interfaces.ejb.*;
import at.team2.server.common.MainBase;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.Serializable;

@Stateless
@Local
@Remote(MainRemote.class)
public class Main extends MainBase implements MainRemote, Serializable {
    @Override
    public int getVersion() {
        return doGetVersion();
    }

    @Override
    public BookRemote getBookRemoteObject() {
        return new Book();
    }

    @Override
    public DvdRemote getDvdRemoteObject() {
        return new Dvd();
    }

    @Override
    public CustomerRemote getCustomerRemoteObject() {
        return new Customer();
    }

    @Override
    public LoanRemote getLoanRemoteObject() {
        return new Loan();
    }

    @Override
    public ReservationRemote getReservationRemoteObject() {
        return new Reservation();
    }

    @Override
    public MediaMemberRemote getMediaMemberRemoteObject() {
        return new MediaMember();
    }

    @Override
    public AccountRemote getAccountRemoteObject() {
        return new Account();
    }

    @Override
    public MessageRemote getMessageRemoteObject() {
        return new Message();
    }
}