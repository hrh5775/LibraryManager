package at.team2.client.entities.session;

import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.interfaces.ejb.CustomerRemote;
import at.team2.common.interfaces.rmi.CustomerRemoteObjectInf;

import java.rmi.RemoteException;
import java.util.List;

public class CustomerRemoteWrapper implements CustomerRemoteObjectInf {
    private CustomerRemote _customerRemote;

    public CustomerRemoteWrapper(CustomerRemote customerRemote) {
        _customerRemote = customerRemote;
    }

    @Override
    public CustomerSmallDto getCustomerSmallById(int id) throws RemoteException {
        return _customerRemote.getCustomerSmallById(id);
    }

    @Override
    public List<CustomerSmallDto> getList(String searchString) throws RemoteException {
        return _customerRemote.getList(searchString);
    }
}