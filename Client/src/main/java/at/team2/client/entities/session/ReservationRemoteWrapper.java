package at.team2.client.entities.session;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.interfaces.ejb.ReservationRemote;
import at.team2.common.interfaces.rmi.ReservationRemoteObjectInf;

import java.rmi.RemoteException;
import java.util.List;

public class ReservationRemoteWrapper implements ReservationRemoteObjectInf {
    private ReservationRemote _reservationRemote;

    public ReservationRemoteWrapper(ReservationRemote reservationRemote) {
        _reservationRemote = reservationRemote;
    }

    @Override
    public List<ReservationDetailedDto> getListByCustomer(int id) throws RemoteException {
        return _reservationRemote.getListByCustomer(id);
    }

    @Override
    public int reserveMedia(MediaSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException {
        return _reservationRemote.reserveMedia(mediaMember, customer, updater);
    }
}