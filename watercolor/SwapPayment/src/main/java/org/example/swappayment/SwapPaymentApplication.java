package org.example.swappayment;

import org.example.swappayment.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJms
public class SwapPaymentApplication {



    public static void main(String[] args) {

        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        System.setProperty("com.ibm.mq.cfg.preferTLS", "true");
        System.out.println("Start");
        SpringApplication.run(SwapPaymentApplication.class, args);
        System.out.println("End");
    }


//
//    @Override
//    public void run(String... args) {
//        Logger logger = LoggerFactory.getLogger(KafkaApplication.class);
//
//        logger.info("------------------------------------");
//        logger.info("Configuration properties:");
//        logger.info("	example.username is {}", configuration.getUsername());
//        logger.info("	example.password is {}", configuration.getPassword());
//        logger.info("-------------------------------------");
//    }
}
