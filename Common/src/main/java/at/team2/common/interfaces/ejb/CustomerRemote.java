package at.team2.common.interfaces.ejb;

import at.team2.common.dto.small.CustomerSmallDto;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface CustomerRemote {
    public CustomerSmallDto getCustomerSmallById(int id);
    public List<CustomerSmallDto> getList(String searchString);
}