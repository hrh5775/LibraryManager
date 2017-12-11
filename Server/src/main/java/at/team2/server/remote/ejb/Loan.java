package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.LoanDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.interfaces.ejb.LoanRemote;
import at.team2.server.common.LoanBase;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.List;

@Stateless
@Local
@Remote(LoanRemote.class)
public class Loan extends LoanBase implements LoanRemote, Serializable {
    @Override
    public List<LoanDetailedDto> getListByCustomer(int id) {
        return doGetListByCustomer(id);
    }

    @Override
    public int loanMediaMember(MediaMemberSmallDto loan, CustomerSmallDto customer, AccountDetailedDto updater) {
        return doLoanMediaMember(loan, customer, updater);
    }

    @Override
    public int takeBackLoan(LoanDetailedDto loan) {
        return doTakeBackLoan(loan);
    }

    @Override
    public boolean extendLoan(LoanDetailedDto loan, AccountDetailedDto updater) {
        return doExtendLoan(loan, updater);
    }

    @Override
    public LoanDetailedDto getLoanDetailedById(int id) {
        return doGetLoanDetailedById(id);
    }

    @Override
    public boolean isLoanPossible(int mediaId, int customerId, boolean isExtend) {
        return doIsLoanPossible(mediaId, customerId, isExtend);
    }
}