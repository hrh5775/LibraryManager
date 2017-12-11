package at.team2.server.remote.rmi;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.interfaces.rmi.MessageRemoteObjectInf;
import at.team2.server.common.MessageBase;
import javax.jms.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageRemoteObject extends UnicastRemoteObject implements MessageRemoteObjectInf {
    private MessageBase _messageBase;

    public MessageRemoteObject() throws RemoteException {
        _messageBase = new MessageBase();
    }

    @Override
    public boolean sendMessageForInterLibraryLoan(String message, AccountDetailedDto updater) throws RemoteException {
        try {
            return _messageBase.doSendMessageForInterLibraryLoan(message, updater);
        } catch (JMSException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public String receiveMessageForInterLibraryLoan(AccountDetailedDto updater) throws RemoteException {
        try {
            return _messageBase.doReceiveMessageForInterLibraryLoan(updater);
        } catch (JMSException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        _messageBase.close();
        super.finalize();
    }
}