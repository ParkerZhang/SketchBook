package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableConfigurationProperties(MyConfiguration.class)
@Profile("App2")
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.@@
public class App2 implements CommandLineRunner {
    private final MyConfiguration configuration;

    public App2(MyConfiguration configuration) {
        this.configuration = configuration;
    }
    public static void main(String[] args) {
        SpringApplication.run(App2.class, args);
    }

    @Override
    public void run(String... args) {
        Logger logger = LoggerFactory.getLogger(App2.class);

        logger.info("------------------------------------");
        logger.info("Configuration properties:");
        logger.info("	example.username is {}", configuration.getUserName());
        logger.info("	example.password is {}", configuration.getPassword());
        logger.info("-------------------------------------");
    }
}