package com.example.jms8.SpringService;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class JMSReceiver {

    // This method listens for messages on the specified destination (Queue)
    @JmsListener(destination = "testQueue")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }
}