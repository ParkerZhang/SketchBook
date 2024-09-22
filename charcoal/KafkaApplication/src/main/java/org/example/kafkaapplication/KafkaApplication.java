package org.example.kafkaapplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {
    private final KafkaSecret configuration;

    public KafkaApplication(KafkaSecret configuration) {
        this.configuration = configuration;
    }
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }


    @Override
    public void run(String... args) {
        Logger logger = LoggerFactory.getLogger(KafkaApplication.class);

        logger.info("------------------------------------");
        logger.info("Configuration properties:");
        logger.info("	example.username is {}", configuration.getUsername());
        logger.info("	example.password is {}", configuration.getPassword());
        logger.info("-------------------------------------");
    }
}
