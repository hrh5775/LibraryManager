package at.team2.common.interfaces.ejb;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.LoanDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface LoanRemote {
    public List<LoanDetailedDto> getListByCustomer(int id);
    public int loanMediaMember(MediaMemberSmallDto loan, CustomerSmallDto customer, AccountDetailedDto updater);
    public int takeBackLoan(LoanDetailedDto loan);
    public boolean extendLoan(LoanDetailedDto loan, AccountDetailedDto updater);

    public LoanDetailedDto getLoanDetailedById(int id);
    public boolean isLoanPossible(int mediaId, int customerId, boolean isExtend);
}