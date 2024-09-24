package org.example.kafkaapplication.VaultService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(KafkaSecret.class)
public class VaultConfig implements CommandLineRunner {
    final KafkaSecret config;

    public VaultConfig(KafkaSecret config) {
        this.config = config;
    }

    @Override
    public void run(String... args) {
        Logger logger = LoggerFactory.getLogger(VaultConfig.class);

        logger.info("------------------------------------");
        logger.info("Configuration properties:");
        logger.info("	example.username is {}", config.getUsername());
        logger.info("	example.password is {}", config.getPassword());
        logger.info("-------------------------------------");
    }

}
