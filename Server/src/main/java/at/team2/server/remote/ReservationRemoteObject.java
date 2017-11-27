package at.team2.server.remote;

import at.team2.application.facade.ReservationApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.interfaces.ReservationRemoteObjectInf;
import at.team2.domain.entities.Reservation;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class ReservationRemoteObject extends UnicastRemoteObject implements ReservationRemoteObjectInf {
    private static Type typeDetailed = new TypeToken<List<ReservationDetailedDto>>() {}.getType();

    protected ReservationRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public List<ReservationDetailedDto> getListByCustomer(int id) throws RemoteException {
        ReservationApplicationFacade facade =  ReservationApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getListByCustomer(id), typeDetailed);
    }

    @Override
    public int reserveMedia(MediaSmallDto media, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException {
        ReservationApplicationFacade facade = ReservationApplicationFacade.getInstance();
        return facade.reserveMedia(media, customer, updater);
    }

    @Override
    public boolean isReserved(MediaMemberSmallDto mediaMember) throws RemoteException
    {
        ReservationApplicationFacade facade = ReservationApplicationFacade.getInstance();
        List<Reservation> list = facade.getList();
        for(Reservation current : list)
        {
            if(mediaMember.getMedia().getStandardMediaId().equals(current.getMedia().getStandardMediaId()))
            {
                return true;
            }
        }
        return false;
    }

}