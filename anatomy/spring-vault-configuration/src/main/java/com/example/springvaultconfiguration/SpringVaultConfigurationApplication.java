package com.example.springvaultconfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MyConfiguration.class)
public class SpringVaultConfigurationApplication implements CommandLineRunner {
	private final MyConfiguration configuration;

	public SpringVaultConfigurationApplication(MyConfiguration configuration) {
		this.configuration = configuration;
	}
	public static void main(String[] args) {

		SpringApplication.run(SpringVaultConfigurationApplication.class, args);
	}
	@Override
	public void run(String... args) {
		Logger logger = LoggerFactory.getLogger(SpringVaultConfigurationApplication.class);

		logger.info("------------------------------------");
		logger.info("Configuration properties:");
		logger.info("	example.username is{}", configuration.getUserName());
		logger.info("	example.password is{}", configuration.getPassword());
		logger.info("-------------------------------------");
	}

}
