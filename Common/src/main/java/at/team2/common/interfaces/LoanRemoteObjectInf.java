package at.team2.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;

public interface LoanRemoteObjectInf extends Remote {
    public int loanMediaMember(MediaMemberSmallDto loan, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException;
}