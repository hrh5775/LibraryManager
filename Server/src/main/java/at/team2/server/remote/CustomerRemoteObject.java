package at.team2.server.remote;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.interfaces.CustomerRemoteObjectInf;
import at.team2.database_wrapper.facade.CustomerFacade;
import at.team2.domain.entities.Customer;

public class CustomerRemoteObject extends UnicastRemoteObject implements CustomerRemoteObjectInf
{
    protected CustomerRemoteObject() throws RemoteException
    {
        super(0);
    }

    @Override
    public CustomerSmallDto getCustomerSmallbyId(int id) throws RemoteException
    {
        CustomerFacade facade = new CustomerFacade();
        ModelMapper mapper = MapperHelper.getMapper();
        Customer customer = facade.getById(id);
        return mapper.map(customer,CustomerSmallDto.class);

    }
}
