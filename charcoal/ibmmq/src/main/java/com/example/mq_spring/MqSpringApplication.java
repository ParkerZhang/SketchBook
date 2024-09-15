package com.example.mq_spring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@SpringBootApplication
@RestController
@EnableJms
public class MqSpringApplication {
    @Autowired
    private JmsTemplate jmsTemplate;
    static boolean warned = false;
    static final String qName = "DEV.QUEUE.1";
    public static void main(String[] args) {
        SpringApplication.run(MqSpringApplication.class, args);
    }

    @GetMapping("send")
    String send(){
        try{
            jmsTemplate.convertAndSend("DEV.QUEUE.1", "Hello World!");
            return "OK";
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @GetMapping("recv")
    String recv(){
        try{
            return jmsTemplate.receiveAndConvert("DEV.QUEUE.1").toString();
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @JmsListener(destination = "DEV.QUEUE.1" )
    public void receiveMessage(String msg) {
        infinityWarning();
        Instant instant=Instant.now();
        System.out.println();
        System.out.println("========================================");
        System.out.println("Received message is: " + msg+ " </t> "+instant.toString());
        System.out.println("========================================");

    }
    void infinityWarning() {
        if (!warned) {
            warned = true;
            System.out.println();
            System.out.println("========================================");
            System.out.println("MQ JMS Listener started for queue: " + MqSpringApplication.qName);
            System.out.println("NOTE: This program does not automatically end - it continues to wait");
            System.out.println("      for more messages, so you may need to hit BREAK to end it.");
            System.out.println("========================================");
        }
    }
}
