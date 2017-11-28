package at.team2.server.remote;

import at.team2.application.facade.ReservationApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.interfaces.ReservationRemoteObjectInf;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ReservationRemoteObject extends UnicastRemoteObject implements ReservationRemoteObjectInf {
    private static Type typeDetailed = new TypeToken<List<ReservationDetailedDto>>() {}.getType();
    private ReservationApplicationFacade _reservationFacade;

    protected ReservationRemoteObject() throws RemoteException {
        super(0);
    }

    private ReservationApplicationFacade getReservationFacade() {
        if(_reservationFacade == null) {
            _reservationFacade = new ReservationApplicationFacade();
        }

        return _reservationFacade;
    }

    @Override
    public List<ReservationDetailedDto> getListByCustomer(int id) throws RemoteException {
        ReservationApplicationFacade facade = getReservationFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getListByCustomer(id), typeDetailed);
    }

    @Override
    public int reserveMedia(MediaSmallDto media, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException {
        ReservationApplicationFacade facade = getReservationFacade();
        return facade.reserveMedia(media, customer, updater);
    }

    @Override
    protected void finalize() throws Throwable {
        if(_reservationFacade != null) {
            _reservationFacade.closeDbSession();
        }

        super.finalize();
    }
}