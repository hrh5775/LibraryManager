package at.team2.server.jms;

import javax.jms.*;

public class MessageSenderHelper {
    public static void produceMessage(ConnectionFactory connectionFactory, Destination destination, String message) throws JMSException {
        MessageProducer messageProducer;
        TextMessage textMessage;

        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        messageProducer = session.createProducer(destination);
        textMessage = session.createTextMessage();

        textMessage.setText(message);
        messageProducer.send(textMessage);

        messageProducer.close();
        session.close();
        connection.close();
    }
}