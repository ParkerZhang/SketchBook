package org.example.springshutdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringShutdownApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringShutdownApplication.class, args);

        // Custom shutdown logic
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ctx.close();
            System.out.println("Application gracefully stopped.");
        }));
    }
}