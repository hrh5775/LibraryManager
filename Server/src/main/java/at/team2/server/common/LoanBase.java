package at.team2.server.common;

import at.team2.application.facade.LoanApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.LoanDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LoanBase {
    private static Type typeDetailed = new TypeToken<List<LoanDetailedDto>>() {}.getType();
    private LoanApplicationFacade _loanFacade;

    private LoanApplicationFacade getLoanFacade() {
        if(_loanFacade == null) {
            _loanFacade = new LoanApplicationFacade();
        }

        return _loanFacade;
    }

    public List<LoanDetailedDto> doGetListByCustomer(int id) {
        LoanApplicationFacade facade = getLoanFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getListByCustomer(id), typeDetailed);
    }

    public int doLoanMediaMember(MediaMemberSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) {
        LoanApplicationFacade facade = getLoanFacade();
        return facade.loanMediaMember(mediaMember, customer, updater);
    }

    public int doTakeBackLoan(LoanDetailedDto loan) {
        LoanApplicationFacade facade = getLoanFacade();
        return facade.takeBackMediaMember(loan);
    }

    public boolean doExtendLoan(LoanDetailedDto loan, AccountDetailedDto updater) {
        LoanApplicationFacade facade = getLoanFacade();
        return facade.extendLoan(loan, updater);
    }

    public LoanDetailedDto doGetLoanDetailedById(int id) {
        LoanApplicationFacade facade = getLoanFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getById(id), LoanDetailedDto.class);
    }

    public boolean doIsLoanPossible(int mediaId, int customerId, boolean isExtend) {
        LoanApplicationFacade facade = getLoanFacade();
        return facade.isLoanPossible(mediaId, customerId, isExtend);
    }

    public void close() {
        if(_loanFacade != null) {
            _loanFacade.closeDbSession();
        }
    }
}