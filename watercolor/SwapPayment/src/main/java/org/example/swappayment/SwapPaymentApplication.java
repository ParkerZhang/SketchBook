package org.example.swappayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SwapPaymentApplication {
    //    private final KafkaSecret configuration;
//
//    public KafkaApplication(KafkaSecret configuration) {
//        this.configuration = configuration;
//    }
    public static void main(String[] args) {
        SpringApplication.run(SwapPaymentApplication.class, args);
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
