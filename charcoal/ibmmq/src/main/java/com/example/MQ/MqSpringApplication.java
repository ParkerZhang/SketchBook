package com.example.MQ;
import com.example.MQ.constant.Constant;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
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

    static final String payment = "PaymentId,Amount\n" +
            "1234567,129.99\n" +
            "1234568,33.60\n" +
            "1234567,129.99\n" +
            "1234568,33.60\n" +
            "1234567,129.99\n" +
            "1234568,33.60";
    public static void main(String[] args) {
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        System.setProperty("com.ibm.mq.cfg.preferTLS", "true");
        System.out.println("Start");
        SpringApplication.run(MqSpringApplication.class, args);

        System.out.println("End");
    }

    @GetMapping("/send")
    String send(){
        try{
            jmsTemplate.convertAndSend(Constant.DESTINATION_NAME, "Hello World!");
            return "OK";
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }
    @GetMapping("/pay")
    String pay(){
        try{
            jmsTemplate.convertAndSend(Constant.DESTINATION_NAME, payment);
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
            return jmsTemplate.receiveAndConvert(Constant.DESTINATION_NAME).toString();
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }

//    @JmsListener(destination = "${mq.DESTINATION_NAME}",containerFactory = "")
//    public void receiveMessage(String msg) {
//        System.out.println("<L>");
//
//        Instant instant=Instant.now();
//        System.out.println();
//        System.out.println("========================================");
//        System.out.println("Received message is: " + msg+ " </t> "+instant.toString());
//        System.out.println("========================================");
//
//    }

}
