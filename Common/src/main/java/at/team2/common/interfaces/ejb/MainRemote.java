package at.team2.common.interfaces.ejb;

import javax.ejb.Remote;

@Remote
public interface MainRemote {
    public int getVersion();
    public BookRemote getBookRemoteObject();
    public DvdRemote getDvdRemoteObject();
    public CustomerRemote getCustomerRemoteObject();
    public LoanRemote getLoanRemoteObject();
    public ReservationRemote getReservationRemoteObject();
    public MediaMemberRemote getMediaMemberRemoteObject();
    public AccountRemote getAccountRemoteObject();
    public MessageRemote getMessageRemoteObject();
}