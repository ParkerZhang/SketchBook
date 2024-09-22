package com.example.filewatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileService {
//    TODO build CSV -> JSON, send JSON to Kafka

//    @Bean
//    public processFile(File file) {
//        System.out.println(file.toPath().getName());
//    }
    public void processFIle() {
        System.out.println("file processed");
    }

}
