package at.team2.server.remote.ejb;

import at.team2.common.interfaces.ejb.*;
import at.team2.server.common.MainBase;

import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.Serializable;

@Stateless
@WebService
@Remote(MainRemote.class)
public class Main extends MainBase implements MainRemote, Serializable {
    @WebMethod
    @Override
    public int getVersion() {
        return doGetVersion();
    }

    @WebMethod(exclude = true)
    @Override
    public BookRemote getBookRemoteObject() {
        return new Book();
    }

    @WebMethod(exclude = true)
    @Override
    public DvdRemote getDvdRemoteObject() {
        return new Dvd();
    }

    @WebMethod(exclude = true)
    @Override
    public CustomerRemote getCustomerRemoteObject() {
        return new Customer();
    }

    @WebMethod(exclude = true)
    @Override
    public LoanRemote getLoanRemoteObject() {
        return new Loan();
    }

    @WebMethod(exclude = true)
    @Override
    public ReservationRemote getReservationRemoteObject() {
        return new Reservation();
    }

    @WebMethod(exclude = true)
    @Override
    public MediaMemberRemote getMediaMemberRemoteObject() {
        return new MediaMember();
    }

    @WebMethod(exclude = true)
    @Override
    public AccountRemote getAccountRemoteObject() {
        return new Account();
    }

    @WebMethod(exclude = true)
    @Override
    public MessageRemote getMessageRemoteObject() {
        return new Message();
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}