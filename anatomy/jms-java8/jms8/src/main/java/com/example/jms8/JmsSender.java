package com.example.jms8;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsSender {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Create a connection factory
            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Create a connection
            connection = factory.createConnection();
            connection.start();

            // Create a session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Define the destination (Queue)
            Destination destination = session.createQueue("testQueue");

            // Create a MessageProducer from the Session to the Queue
            MessageProducer producer = session.createProducer(destination);

            // Create a message
            for (int i = 1; i <= 5; i++) {
                TextMessage message = session.createTextMessage("Hello, this is message " + i);
                // Send the message
                producer.send(message);
                System.out.println("Sent: " + message.getText());
            }

            // Cleanup
            producer.close();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}