package at.team2.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import at.team2.common.dto.small.LoanSmallDto;

public interface LoanRemoteObjectInf extends Remote {
    public void addLoan(LoanSmallDto loan) throws RemoteException;
}