package at.team2.server.remote.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.LoanDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.interfaces.rmi.LoanRemoteObjectInf;
import at.team2.server.common.LoanBase;

public class LoanRemoteObject extends UnicastRemoteObject implements LoanRemoteObjectInf {
    private LoanBase _loanBase;

    public LoanRemoteObject() throws RemoteException {
        _loanBase = new LoanBase();
    }

    @Override
    public List<LoanDetailedDto> getListByCustomer(int id) throws RemoteException {
        return _loanBase.doGetListByCustomer(id);
    }

    @Override
    public int loanMediaMember(MediaMemberSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException {
        return _loanBase.doLoanMediaMember(mediaMember, customer, updater);
    }

    @Override
    public int takeBackLoan(LoanDetailedDto loan) throws RemoteException {
        return _loanBase.doTakeBackLoan(loan);
    }

    @Override
    public boolean extendLoan(LoanDetailedDto loan, AccountDetailedDto updater) throws RemoteException {
        return _loanBase.doExtendLoan(loan, updater);
    }

    @Override
    public LoanDetailedDto getLoanDetailedById(int id) throws RemoteException {
        return _loanBase.doGetLoanDetailedById(id);
    }

    @Override
    public boolean isLoanPossible(int mediaId, int customerId, boolean isExtend) throws RemoteException {
        return _loanBase.doIsLoanPossible(mediaId, customerId, isExtend);
    }

    @Override
    protected void finalize() throws Throwable {
        _loanBase.close();
        super.finalize();
    }
}