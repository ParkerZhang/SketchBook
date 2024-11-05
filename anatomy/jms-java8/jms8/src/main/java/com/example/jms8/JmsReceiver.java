package com.example.jms8;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsReceiver {
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

            // Create a MessageConsumer from the Session to the Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Set a MessageListener to process messages asynchronously
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        try {
                            TextMessage textMessage = (TextMessage) message;
                            System.out.println("Received: " + textMessage.getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            // Ensure you close the connection in your actual application
            // connection.close();
        }
    }
}