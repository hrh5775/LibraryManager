package at.team2.server.common;

import at.team2.application.SessionManager;
import at.team2.application.enums.Role;
import at.team2.application.helper.RoleHelper;
import at.team2.common.dto.detailed.AccountDetailedDto;
import at.team2.server.helper.FileWriterHelper;
import at.team2.server.jms.MessageReceiverHelper;
import at.team2.server.jms.MessageSenderHelper;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MessageBase {
    @Resource(mappedName = "jms/remoteLoanConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/remoteLoan")
    private Queue destination;
    private String debuggingDirectory = "debugging";
    private String interlibraryLoanSendDirectory = debuggingDirectory + "/" + "interlibrary_loan_send";
    private String interlibraryLoanReceiveDirectory = debuggingDirectory + "/" + "interlibrary_loan_receive";

    public boolean doSendMessageForInterLibraryLoan(String message, AccountDetailedDto updater) throws JMSException {
        updater = SessionManager.getInstance().getSession(updater);

        if (updater != null &&
                (RoleHelper.hasRole(updater, Role.BIBLIOTHEKAR))) {
            MessageSenderHelper.produceMessage(connectionFactory, destination, message);

            try {
                FileWriterHelper.writeFile("produced: " + new Date().toString() + "\n" +
                        "message: " + "'" + message + "'" + "\n\n", "loan_message_sent.txt", interlibraryLoanSendDirectory + getDirectory(), true, true);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public String doReceiveMessageForInterLibraryLoan(AccountDetailedDto account) throws JMSException {
        account = SessionManager.getInstance().getSession(account);

        if (account != null &&
                (RoleHelper.hasRole(account, Role.OPERATOR))) {
            String message = MessageReceiverHelper.getMessage(connectionFactory, destination);

            try {
                FileWriterHelper.writeFile("produced: " + new Date().toString() + "\n" +
                        "message: " + "'" + message + "'" + "\n\n", "loan_message_received.txt", interlibraryLoanReceiveDirectory + getDirectory(), true, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return message;
        }

        return null;
    }

    public void close() {
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