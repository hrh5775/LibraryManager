package at.team2.server.remote.ejb;

import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.common.interfaces.ejb.MessageRemote;
import at.team2.server.common.MessageBase;

import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import java.io.Serializable;

@Stateless
@Local
@Remote(MessageRemote.class)
public class Message extends MessageBase implements MessageRemote, Serializable {
    @Override
    public boolean sendMessageForInterLibraryLoan(String message, AccountDetailedDto updater) {
        try {
            return doSendMessageForInterLibraryLoan(message, updater);
        } catch (JMSException e) {
            return false;
        }
    }

    @Override
    public String receiveMessageForInterLibraryLoan(AccountDetailedDto updater) {
        try {
            return doReceiveMessageForInterLibraryLoan(updater);
        } catch (JMSException e) {
            return null;
        }
    }

    @PreDestroy
    private void preDestroy() {
        close();
    }
}