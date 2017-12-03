package at.team2.server.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

// @todo: remove this
@Startup
public class MessageSender
{
    @Resource(mappedName = "jms/remoteLoanConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/remoteLoan")
    private static Queue queue;

    public void produceMessages()
    {
        MessageProducer messageProducer;
        TextMessage textMessage;
        try
        {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
            textMessage = session.createTextMessage();

            textMessage.setText("Testing, 1, 2, 3. Can you hear me?");
            System.out.println("Sending the following message: "
                    + textMessage.getText());
            messageProducer.send(textMessage);

            textMessage.setText("Do you copy?");
            System.out.println("Sending the following message: "
                    + textMessage.getText());
            messageProducer.send(textMessage);

            textMessage.setText("Good bye!");
            System.out.println("Sending the following message: "
                    + textMessage.getText());
            messageProducer.send(textMessage);

            messageProducer.close();
            session.close();
            connection.close();
        }
        catch (JMSException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        System.out.println("start");
        new MessageSender().produceMessages();
        System.out.println("end");
    }
}
