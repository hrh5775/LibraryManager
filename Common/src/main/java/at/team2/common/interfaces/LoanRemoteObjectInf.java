package at.team2.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;

public interface LoanRemoteObjectInf extends Remote {
    public int loanMedia(MediaSmallDto loan, CustomerSmallDto customer) throws RemoteException;
}