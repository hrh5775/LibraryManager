package at.team2.server.ejb;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MessageSender {
    
    @Resource(mappedName = "jms/remoteLoanConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/remoteLoan")
    private static Queue queue;

    public void produceMessages(String message) {
        MessageProducer messageProducer;
        TextMessage textMessage;

        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
            textMessage = session.createTextMessage();

            textMessage.setText(message);
            messageProducer.send(textMessage);

            messageProducer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        System.out.println("start");
        String message = "test";

        //aufrufen um Message zu produzieren
        new MessageSender().produceMessages(message);
        System.out.println("end");
    }
}
