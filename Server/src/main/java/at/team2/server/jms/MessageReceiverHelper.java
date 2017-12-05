package at.team2.server.jms;

import javax.jms.*;
public class MessageReceiverHelper {
    public static String getMessage(ConnectionFactory connectionFactory, Destination destination) throws JMSException {
        Connection connection;
        MessageConsumer messageConsumer;
        TextMessage textMessage;

        connection = connectionFactory.createConnection();
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        messageConsumer = session.createConsumer(destination);
        connection.start();

     //   System.out.println("Waiting for messages...");
        textMessage = (TextMessage) messageConsumer.receive();

     /*
        if (textMessage != null)
        {
            System.out.print("Received the following message: ");
            System.out.println(textMessage.getText());
            System.out.println();
        }
        */
        messageConsumer.close();
        session.close();
        connection.close();
        return textMessage.getText();
    }
}
