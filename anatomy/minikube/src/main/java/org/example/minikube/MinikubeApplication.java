package org.example.minikube;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class MinikubeApplication {

    static AppConfig appConfig;
    public static void main(String[] args) {

        System.out.printf("Application %s started!\n",appConfig.appName);
        SpringApplication.run(MinikubeApplication.class, args);
    }
    @GetMapping("/hello")
    public String hello() {
        return String.format("Hello, %s! greeting from %s.",appConfig.username,appConfig.hostname);
    }
}
