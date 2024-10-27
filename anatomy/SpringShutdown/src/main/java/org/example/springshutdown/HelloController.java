package org.example.springshutdown;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@EnableScheduling
public class HelloController {
    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        for (int i = 0;i< 10; i++) {
            System.out.println(i);
            Thread.sleep(1000);
        }
        return "Hello, World!";
    }


    @Scheduled(fixedDelay = 5000)
    public void task1() {

        try {
            for (int i = 0;i< 10; i++) {
                System.out.println(i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sleeping for 5 seconds...");

    }
}

