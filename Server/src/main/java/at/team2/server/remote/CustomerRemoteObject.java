package at.team2.server.remote;

import at.team2.application.facade.CustomerApplicationFacade;
import org.modelmapper.ModelMapper;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.interfaces.CustomerRemoteObjectInf;
import at.team2.domain.entities.Customer;

public class CustomerRemoteObject extends UnicastRemoteObject implements CustomerRemoteObjectInf {
    protected CustomerRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public CustomerSmallDto getCustomerSmallById(int id) throws RemoteException {
        CustomerApplicationFacade facade = CustomerApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();
        Customer entity = facade.getById(id);

        if(entity != null) {
            return mapper.map(entity, CustomerSmallDto.class);
        }

        return null;
    }
}