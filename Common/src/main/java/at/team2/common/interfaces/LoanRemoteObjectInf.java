package at.team2.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.LoanDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;

public interface LoanRemoteObjectInf extends Remote {
    public List<LoanDetailedDto> getListByCustomer(int id) throws RemoteException;
    public int loanMediaMember(MediaMemberSmallDto loan, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException;
    public int takeBackLoan(LoanDetailedDto loan) throws RemoteException;
    public boolean extendLoan(LoanDetailedDto loan, AccountDetailedDto updater) throws RemoteException;

    public LoanDetailedDto getLoanDetailedById(int id) throws RemoteException;
    public boolean isLoanPossible(int mediaId, int customerId, boolean isExtend) throws RemoteException;
}