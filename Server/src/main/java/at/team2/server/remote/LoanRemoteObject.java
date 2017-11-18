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

    protected LoanRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public List<LoanDetailedDto> getListByCustomer(int id) throws RemoteException {
        LoanApplicationFacade facade =  LoanApplicationFacade.getInstance();
        ModelMapper mapper = MapperHelper.getMapper();

        return mapper.map(facade.getListByCustomer(id), typeDetailed);
    }

    @Override
    public int loanMediaMember(MediaMemberSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException {
        LoanApplicationFacade facade = LoanApplicationFacade.getInstance();
        return facade.loanMediaMember(mediaMember, customer, updater);
    }

    @Override
    public int takeBackLoan(LoanDetailedDto loan) throws RemoteException {
        LoanApplicationFacade facade = LoanApplicationFacade.getInstance();
        facade.takeBackMediaMember(loan);
        return 0;
    }
}