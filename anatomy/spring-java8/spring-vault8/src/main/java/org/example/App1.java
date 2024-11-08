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

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.@@
@Profile("App1")
public class App1  {


    public static void main(String[] args) {
        SpringApplication.run(App1.class, args);
    }


    @Value("${mykey}")
    String mykey;

    @Value("${hello.world}")
    String helloWorld;

    @PostConstruct
    private void postConstruct() {
        System.out.println("##########################");
        System.out.println(mykey);
        System.out.println(helloWorld);
        System.out.println("##########################");
    }
}