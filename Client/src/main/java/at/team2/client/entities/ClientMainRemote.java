package at.team2.client.entities;

import at.team2.common.interfaces.ejb.*;

public class ClientMainRemote implements at.team2.common.interfaces.ejb.MainRemote {
    private AccountRemote _accountRemote;
    private BookRemote _bookRemote;
    private CustomerRemote _customerRemote;
    private DvdRemote _dvdRemote;
    private LoanRemote _loanRemote;
    private at.team2.common.interfaces.ejb.MainRemote _mainRemote;
    private MediaMemberRemote _mediaMemberRemote;
    private MessageRemote _messageRemote;
    private ReservationRemote _reservationRemote;

    public ClientMainRemote(AccountRemote accountRemote, BookRemote bookRemote, CustomerRemote customerRemote,
                            DvdRemote dvdRemote, LoanRemote loanRemote, at.team2.common.interfaces.ejb.MainRemote mainRemote,
                            MediaMemberRemote mediaMemberRemote, MessageRemote messageRemote,
                            ReservationRemote reservationRemote) {
        _accountRemote = accountRemote;
        _bookRemote = bookRemote;
        _customerRemote = customerRemote;
        _dvdRemote = dvdRemote;
        _loanRemote = loanRemote;
        _mainRemote = mainRemote;
        _mediaMemberRemote = mediaMemberRemote;
        _messageRemote = messageRemote;
        _reservationRemote = reservationRemote;
    }

    @Override
    public int getVersion() {
        return _mainRemote.getVersion();
    }

    @Override
    public BookRemote getBookRemoteObject() {
        return _bookRemote;
    }

    @Override
    public DvdRemote getDvdRemoteObject() {
        return _dvdRemote;
    }

    @Override
    public CustomerRemote getCustomerRemoteObject() {
        return _customerRemote;
    }

    @Override
    public LoanRemote getLoanRemoteObject() {
        return _loanRemote;
    }

    @Override
    public ReservationRemote getReservationRemoteObject() {
        return _reservationRemote;
    }

    @Override
    public MediaMemberRemote getMediaMemberRemoteObject() {
        return _mediaMemberRemote;
    }

    @Override
    public AccountRemote getAccountRemoteObject() {
        return _accountRemote;
    }

    @Override
    public MessageRemote getMessageRemoteObject() {
        return _messageRemote;
    }
}