package org.example.swappayment.FileWatcherService;

import org.example.swappayment.DataService.DataService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileService {
//    TODO build CSV -> JSON, send JSON to Kafka
final DataService dataService;

    public FileService(DataService dataService) {
        this.dataService = dataService;
    }

    //    @Bean
    public void processFile(File file) {
        dataService.processCSV(file);
        System.out.println(file.getAbsoluteFile().getAbsoluteFile().toString() + " processed");
    }


}
