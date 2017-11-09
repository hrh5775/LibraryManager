package at.team2.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import at.team2.application.facade.LoanApplicationFacade;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaMemberSmallDto;
import at.team2.common.interfaces.LoanRemoteObjectInf;

public class LoanRemoteObject extends UnicastRemoteObject implements LoanRemoteObjectInf {
    protected LoanRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public int loanMediaMember(MediaMemberSmallDto mediaMember, CustomerSmallDto customer, AccountDetailedDto updater) throws RemoteException {
        LoanApplicationFacade facade = LoanApplicationFacade.getInstance();
        return facade.loanMediaMember(mediaMember, customer, updater);
    }
}