package at.team2.server.remote.ejb;

import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.interfaces.ejb.CustomerRemote;
import at.team2.server.common.CustomerBase;

import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.Serializable;
import java.util.List;

@Stateless
@WebService
@Remote(CustomerRemote.class)
public class Customer extends CustomerBase implements CustomerRemote, Serializable {
    @WebMethod
    @Override
    public CustomerSmallDto getCustomerSmallById(int id) {
        return doGetCustomerSmallById(id);
    }

    @WebMethod
    @Override
    public List<CustomerSmallDto> getList(String searchString) {
        return doGetList(searchString);
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}