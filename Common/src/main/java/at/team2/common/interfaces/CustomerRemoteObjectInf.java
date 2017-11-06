package at.team2.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import at.team2.common.dto.small.CustomerSmallDto;

public interface CustomerRemoteObjectInf extends Remote
{
    public CustomerSmallDto getCustomerSmallbyId(int id) throws RemoteException;
}
