package at.team2.server.common;

import at.team2.application.facade.ReservationApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class ReservationBase {
    private static Type _typeDetailed = new TypeToken<List<ReservationDetailedDto>>() {}.getType();
    private ReservationApplicationFacade _reservationFacade;

    private ReservationApplicationFacade getReservationFacade() {
        if(_reservationFacade == null) {
            _reservationFacade = new ReservationApplicationFacade();
        }

        return _reservationFacade;
    }

    public List<ReservationDetailedDto> doGetListByCustomer(int id) {
        ReservationApplicationFacade facade = getReservationFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getListByCustomer(id), _typeDetailed);
    }

    public int doReserveMedia(MediaSmallDto media, CustomerSmallDto customer, AccountDetailedDto updater) {
        ReservationApplicationFacade facade = getReservationFacade();
        return facade.reserveMedia(media, customer, updater);
    }

    public void close() {
        if(_reservationFacade != null) {
            _reservationFacade.closeDbSession();
        }
    }
}