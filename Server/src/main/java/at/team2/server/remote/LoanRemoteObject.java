package at.team2.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import at.team2.application.facade.CustomerApplicationFacade;
import at.team2.application.facade.LoanApplicationFacade;
import at.team2.application.facade.MediaMemberApplicationFacade;
import at.team2.common.dto.small.LoanSmallDto;
import at.team2.common.interfaces.LoanRemoteObjectInf;
import at.team2.domain.entities.Customer;
import at.team2.domain.entities.Loan;
import at.team2.domain.entities.MediaMember;

public class LoanRemoteObject extends UnicastRemoteObject implements LoanRemoteObjectInf
{
    protected LoanRemoteObject() throws RemoteException
    {
     super(0);
    }

    @Override
    public void AddLoan(LoanSmallDto loan)
    {
        LoanApplicationFacade facade = LoanApplicationFacade.getInstance();
        facade.add(loan);

    }
}
