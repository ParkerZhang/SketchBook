package org.example.minikube;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class Heartbeat {
    @Scheduled(fixedRate = 5000)
    public void heartbeat() {
        System.out.println("heartbeat");
    }
}
