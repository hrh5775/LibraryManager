package at.team2.server.remote;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import at.team2.application.facade.LoanApplicationFacade;
import at.team2.application.helper.MapperHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.detailed.LoanDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.interfaces.LoanRemoteObjectInf;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

public class LoanRemoteObject extends UnicastRemoteObject implements LoanRemoteObjectInf {
    private static Type typeDetailed = new TypeToken<List<LoanDetailedDto>>() {}.getType();
    private LoanApplicationFacade _loanFacade;

    protected LoanRemoteObject() throws RemoteException {
        super(0);
    }

    private LoanApplicationFacade getLoanFacade() {
        if(_loanFacade == null) {
            _loanFacade = new LoanApplicationFacade();
        }

        return _loanFacade;
    }

    @Override
    public List<LoanDetailedDto> getListByCustomer(int id) throws RemoteException {
        LoanApplicationFacade facade = getLoanFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getListByCustomer(id), typeDetailed);
    }

    @Override
    public int loanMediaMember(MediaMemberSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException {
        LoanApplicationFacade facade = getLoanFacade();
        return facade.loanMediaMember(mediaMember, customer, updater);
    }

    @Override
    public int takeBackLoan(LoanDetailedDto loan) throws RemoteException {
        LoanApplicationFacade facade = getLoanFacade();
        return facade.takeBackMediaMember(loan);
    }

    @Override
    public boolean extendLoan(LoanDetailedDto loan, AccountDetailedDto updater) {
        LoanApplicationFacade facade = getLoanFacade();
        return facade.extendLoan(loan, updater);
    }

    @Override
    public LoanDetailedDto getLoanDetailedById(int id) throws RemoteException {
        LoanApplicationFacade facade = getLoanFacade();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getById(id), LoanDetailedDto.class);
    }

    @Override
    public boolean isLoanPossible(int mediaId, int customerId, boolean isExtend) throws RemoteException {
        LoanApplicationFacade facade = getLoanFacade();
        return facade.isLoanPossible(mediaId, customerId, isExtend);
    }

    @Override
    protected void finalize() throws Throwable {
        if(_loanFacade != null) {
            _loanFacade.closeDbSession();
        }

        super.finalize();
    }
}