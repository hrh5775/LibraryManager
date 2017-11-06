package at.team2.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import at.team2.application.facade.LoanApplicationFacade;
import at.team2.common.dto.small.LoanSmallDto;
import at.team2.common.interfaces.LoanRemoteObjectInf;

public class LoanRemoteObject extends UnicastRemoteObject implements LoanRemoteObjectInf {
    protected LoanRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public void addLoan(LoanSmallDto loan) throws RemoteException {
        LoanApplicationFacade facade = LoanApplicationFacade.getInstance();
        facade.add(loan);
    }
}