package at.team2.server.common;

import at.team2.application.facade.CustomerApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.domain.entities.Customer;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CustomerBase {
    private static Type typeSmall = new TypeToken<List<CustomerSmallDto>>() {}.getType();
    private CustomerApplicationFacade _customerFacade;

    private CustomerApplicationFacade getCustomerFacade() {
        if(_customerFacade == null) {
            _customerFacade = new CustomerApplicationFacade();
        }

        return _customerFacade;
    }

    public CustomerSmallDto doGetCustomerSmallById(int id) {
        CustomerApplicationFacade facade = getCustomerFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Customer entity = facade.getById(id);

        if(entity != null) {
            return mapper.map(entity, CustomerSmallDto.class);
        }

        return null;
    }

    public List<CustomerSmallDto> doGetList(String searchString) {
        CustomerApplicationFacade facade = getCustomerFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.search(searchString), typeSmall);
    }

    public void close() {
        if(_customerFacade != null) {
            _customerFacade.closeDbSession();
        }
    }
}