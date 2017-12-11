package at.team2.common.interfaces.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import at.team2.common.dto.small.CustomerSmallDto;

public interface CustomerRemoteObjectInf extends Remote {
    public CustomerSmallDto getCustomerSmallById(int id) throws RemoteException;
    public List<CustomerSmallDto> getList(String searchString) throws RemoteException;
}