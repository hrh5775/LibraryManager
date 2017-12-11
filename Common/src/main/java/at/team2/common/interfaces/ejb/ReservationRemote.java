package at.team2.common.interfaces.ejb;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.ReservationDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ReservationRemote {
    public List<ReservationDetailedDto> getListByCustomer(int id);
    public int reserveMedia(MediaSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater);
}