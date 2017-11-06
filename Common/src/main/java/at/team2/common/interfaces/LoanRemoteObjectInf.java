package at.team2.common.interfaces;

import java.rmi.Remote;

import at.team2.common.dto.small.LoanSmallDto;

public interface LoanRemoteObjectInf extends Remote {
    public void addLoan(LoanSmallDto loan);
}