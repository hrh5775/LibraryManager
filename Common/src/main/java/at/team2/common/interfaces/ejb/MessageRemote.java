package at.team2.common.interfaces.ejb;

import at.team2.common.dto.detailed.AccountDetailedDto;

import javax.ejb.Remote;

@Remote
public interface MessageRemote {
    public boolean sendMessageForInterLibraryLoan(String message, AccountDetailedDto updater);
    public String receiveMessageForInterLibraryLoan(AccountDetailedDto updater);
}