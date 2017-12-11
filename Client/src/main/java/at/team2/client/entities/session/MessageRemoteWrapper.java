package at.team2.client.entities.session;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.interfaces.ejb.MessageRemote;
import at.team2.common.interfaces.rmi.MessageRemoteObjectInf;
import java.rmi.RemoteException;

public class MessageRemoteWrapper implements MessageRemoteObjectInf {
    private MessageRemote _messageRemote;

    public MessageRemoteWrapper(MessageRemote messageRemote) {
        _messageRemote = messageRemote;
    }

    @Override
    public boolean sendMessageForInterLibraryLoan(String message, AccountDetailedDto updater) throws RemoteException {
        return _messageRemote.sendMessageForInterLibraryLoan(message, updater);
    }

    @Override
    public String receiveMessageForInterLibraryLoan(AccountDetailedDto updater) throws RemoteException {
        return _messageRemote.receiveMessageForInterLibraryLoan(updater);
    }
}