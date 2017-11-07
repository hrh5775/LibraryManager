package at.team2.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;

public interface LoanRemoteObjectInf extends Remote {
    public int loanMediaMember(MediaMemberSmallDto loan, CustomerSmallDto customer) throws RemoteException;
}