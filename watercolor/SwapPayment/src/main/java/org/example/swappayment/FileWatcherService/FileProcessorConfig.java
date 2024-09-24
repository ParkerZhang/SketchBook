package org.example.swappayment.FileWatcherService;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Configuration
@EnableScheduling
public class FileProcessorConfig {
    final FileService fileService;
    private static final String DIRECTORY_PATH = "/home/data/in/";
    private static final long TIME_THRESHOLD = 5000; // 5 seconds

    public FileProcessorConfig(FileService fileService) {
        this.fileService = fileService;
    }

    @Scheduled(fixedRate = 10000) // Run every 10 seconds
    public void processFiles() {
        File directory = new File(DIRECTORY_PATH);
        String archive = "/home/data/out/";

        Path archivePath = Paths.get(archive);
        if (!Files.exists(archivePath) ){
            try {
                Files.createDirectories(archivePath);
            }
            catch (IOException e) { System.out.println("Unable to create archive directory"); }
        }

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                Arrays.stream(files)
                        .filter(file -> file.isFile() && file.lastModified() < System.currentTimeMillis() - TIME_THRESHOLD)
                        .forEach(this::processFile);
            }
        }
    }

    private void processFile(File file) {
        // Implement your file processing logic here
        System.out.println("Processing file: " + file.getName());
        fileService.processFile(file);
        try {
            Path archivePath = Paths.get("/home/data/out/" , file.getName() );
            Files.move(file.toPath(),archivePath, StandardCopyOption.REPLACE_EXISTING) ;
        } catch (IOException e) {
            System.out.println("Error moving file: " + file.getName() ) ;
        }
    }
}
