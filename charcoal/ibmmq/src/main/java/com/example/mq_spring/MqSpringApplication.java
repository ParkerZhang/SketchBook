package com.example.mq_spring;
import com.ibm.msg.client.jakarta.jms.JmsConnectionFactory;
import com.ibm.msg.client.jakarta.jms.JmsFactoryFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
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
//
//    @Autowired
//    private ConnectionFactory connectionFactory;
    public static void main(String[] args) {
        System.out.println("Start");
        SpringApplication.run(MqSpringApplication.class, args);
        System.out.println("End");
    }

    @GetMapping("/send")
    String send(){
        try{
            jmsTemplate.convertAndSend("DEV.QUEUE.1", "Hello World!");
            return "OK";
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

    @GetMapping("/recv")
    String recv(){
        System.out.println("/recv");
        try{
            return jmsTemplate.receiveAndConvert("DEV.QUEUE.1").toString();
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

//    @JmsListener(destination = "DEV.QUEUE.1" ,containerFactory = "")
//    public void receiveMessage(String msg) {
//        System.out.println("<L>");
//        infinityWarning();
//        Instant instant=Instant.now();
//        System.out.println();
//        System.out.println("========================================");
//        System.out.println("Received message is: " + msg+ " </t> "+instant.toString());
//        System.out.println("========================================");
//
//    }

//    @Bean ("myGetContainerFactory102")
//    public JmsListenerContainerFactory<?> myGetContainerFactory102() {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
////        factory.setPubSubDomain(false);
//        return factory;
//    }
//    private JmsConnectionFactory createJMSConnectionFactory() {
//        JmsFactoryFactory ff;
//        JmsConnectionFactory cf;
//        try {
//            // JMS
//            ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
//            // Jakarta
//            // ff = JmsFactoryFactory.getInstance(WMQConstants.JAKARTA_WMQ_PROVIDER);
//
//            cf = ff.createConnectionFactory();
//        } catch (JMSException jmsex) {
//            recordFailure(jmsex);
//            cf = null;
//        }
//        return cf;
//    }

    private void recordFailure(JMSException jmsex) {
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

//    @Bean("jmsTemplate")
//    public JmsTemplate jmsTemplate() throws JMSException {
////         return new JmsTemplate(mqConnectionFactory());
//
//        System.out.println();
//        System.out.println("========================================");
//        System.out.println("MQ JMS initializing JmsTemplatefor queue: " + MqSpringApplication.qName);
//
//        System.out.println("========================================");
//        return new  JmsTemplate();
//    }
//    @Bean("jmsTemplate")
//    public JmsTemplate jmsTemplate() {
//        //return new JmsTemplate(connectionFactory);
//        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
//        if (pubsub) {
//            jmsTemplate.setPubSubDomain(true);
//        } else {
//            jmsTemplate.setPubSubDomain(false);
//        }
//        return jmsTemplate;
//    }
}
