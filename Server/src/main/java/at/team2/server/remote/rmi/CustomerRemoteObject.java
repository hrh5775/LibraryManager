package at.team2.server.remote.rmi;

import at.team2.server.common.CustomerBase;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.interfaces.rmi.CustomerRemoteObjectInf;

public class CustomerRemoteObject extends UnicastRemoteObject implements CustomerRemoteObjectInf {
    private CustomerBase _customerBase;

    public CustomerRemoteObject() throws RemoteException {
        _customerBase = new CustomerBase();
    }

    @Override
    public CustomerSmallDto getCustomerSmallById(int id) throws RemoteException {
        return _customerBase.doGetCustomerSmallById(id);
    }

    @Override
    public List<CustomerSmallDto> getList(String searchString) throws RemoteException {
        return _customerBase.doGetList(searchString);
    }

    @Override
    protected void finalize() throws Throwable {
        _customerBase.close();
        super.finalize();
    }
}