package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.common.interfaces.ejb.ReservationRemote;
import at.team2.server.common.ReservationBase;

import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.Serializable;
import java.util.List;

@Stateless
@WebService
@Remote(ReservationRemote.class)
public class Reservation extends ReservationBase implements ReservationRemote, Serializable {
    @WebMethod
    @Override
    public List<ReservationDetailedDto> getListByCustomer(int id) {
        return doGetListByCustomer(id);
    }

    @WebMethod
    @Override
    public int reserveMedia(MediaSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) {
        return doReserveMedia(mediaMember, customer, updater);
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}