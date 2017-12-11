package at.team2.server.remote.rmi;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.interfaces.rmi.ReservationRemoteObjectInf;

import at.team2.server.common.ReservationBase;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ReservationRemoteObject extends UnicastRemoteObject implements ReservationRemoteObjectInf {
    private ReservationBase _reservationBase;

    public ReservationRemoteObject() throws RemoteException {
        _reservationBase = new ReservationBase();
    }

    @Override
    public List<ReservationDetailedDto> getListByCustomer(int id) throws RemoteException {
        return _reservationBase.doGetListByCustomer(id);
    }

    @Override
    public int reserveMedia(MediaSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException {
        return _reservationBase.doReserveMedia(mediaMember, customer, updater);
    }

    @Override
    protected void finalize() throws Throwable {
        _reservationBase.close();
        super.finalize();
    }
}