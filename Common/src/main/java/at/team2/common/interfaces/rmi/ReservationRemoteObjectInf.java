package at.team2.common.interfaces.rmi;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ReservationRemoteObjectInf extends Remote {
    public List<ReservationDetailedDto> getListByCustomer(int id) throws RemoteException;
    public int reserveMedia(MediaSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException;
}