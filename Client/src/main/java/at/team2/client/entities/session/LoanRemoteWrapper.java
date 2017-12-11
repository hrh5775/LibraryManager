package at.team2.client.entities.session;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.LoanDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.interfaces.ejb.LoanRemote;
import at.team2.common.interfaces.rmi.LoanRemoteObjectInf;

import java.rmi.RemoteException;
import java.util.List;

public class LoanRemoteWrapper implements LoanRemoteObjectInf {
    private LoanRemote _loanRemote;

    public LoanRemoteWrapper(LoanRemote loanRemote) {
        _loanRemote = loanRemote;
    }

    @Override
    public List<LoanDetailedDto> getListByCustomer(int id) throws RemoteException {
        return _loanRemote.getListByCustomer(id);
    }

    @Override
    public int loanMediaMember(MediaMemberSmallDto loan, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException {
        return _loanRemote.loanMediaMember(loan, customer, updater);
    }

    @Override
    public int takeBackLoan(LoanDetailedDto loan) throws RemoteException {
        return _loanRemote.takeBackLoan(loan);
    }

    @Override
    public boolean extendLoan(LoanDetailedDto loan, AccountDetailedDto updater) throws RemoteException {
        return _loanRemote.extendLoan(loan, updater);
    }

    @Override
    public LoanDetailedDto getLoanDetailedById(int id) throws RemoteException {
        return _loanRemote.getLoanDetailedById(id);
    }

    @Override
    public boolean isLoanPossible(int mediaId, int customerId, boolean isExtend) throws RemoteException {
        return _loanRemote.isLoanPossible(mediaId, customerId, isExtend);
    }
}