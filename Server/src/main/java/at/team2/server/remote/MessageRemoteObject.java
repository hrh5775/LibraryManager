package at.team2.server.remote;

import at.team2.common.interfaces.MessageRemoteObjectInf;
import at.team2.server.helper.FileWriterHelper;
import at.team2.server.jms.MessageReceiverHelper;
import at.team2.server.jms.MessageSenderHelper;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;

@Stateless
@Remote(MessageRemoteObjectInf.class)
public class MessageRemoteObject extends UnicastRemoteObject implements MessageRemoteObjectInf {
    @Resource(mappedName = "jms/remoteLoanConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/remoteLoan")
    private Queue destination;
    private String debuggingDirectory = "debugging";
    private String interlibraryLoanDirectory = debuggingDirectory + "/" + "interlibrary_loan";

    public MessageRemoteObject() throws RemoteException {
        super(0);
    }

    @Override
    public boolean sendMessageForInterLibraryLoan(String message) throws RemoteException {
        try {
            MessageSenderHelper.produceMessage(connectionFactory, destination, message);
        } catch (JMSException e) {
            throw new RemoteException(e.toString());
        }

        try {
            FileWriterHelper.writeFile("produced: " + new Date().toString() + "\n" +
                    "message: " + "'" + message + "'" + "\n\n", "loan_message_send.txt", interlibraryLoanDirectory + getDirectory(), true, true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String receiveMessageForInterLibraryLoan() throws RemoteException {
        try {
           return MessageReceiverHelper.getMessage(connectionFactory, destination);
        } catch (JMSException e) {
            throw new RemoteException(e.toString());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    private String getDirectory() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return  "/" + year + "_" + month + "_" + day + "__" + hour + "_" + minute;
    }
}