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
import java.io.Serializable;
import java.util.List;

@Stateless
@Local
@Remote(ReservationRemote.class)
public class Reservation extends ReservationBase implements ReservationRemote, Serializable {
    @Override
    public List<ReservationDetailedDto> getListByCustomer(int id) {
        return doGetListByCustomer(id);
    }

    @Override
    public int reserveMedia(MediaSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) {
        return doReserveMedia(mediaMember, customer, updater);
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}