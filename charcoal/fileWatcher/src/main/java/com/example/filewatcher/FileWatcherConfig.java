package com.example.filewatcher;

import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.util.Set;

@Configuration
public class FileWatcherConfig {
    FileService fileService;
    @Bean
    public FileSystemWatcher fileSystemWatcher(FileService fileservice) throws IOException {
        this.fileService = fileservice;
        String watchFolder ="home/data/in";
        String archive = "/home/data/out/";

        Path archivePath = Paths.get(archive);

        if (!Files.exists(archivePath) ){
            Files.createDirectories(archivePath);
        }

        FileSystemWatcher watcher = new FileSystemWatcher(true, Duration.ofMillis(10), Duration.ofMillis(5));
        watcher.addSourceDirectory(new File("/home/data/in"));
        watcher.addListener(new FileChangeListener() {
            @Override
            public void onChange(Set<ChangedFiles> changeSet) {
                for (ChangedFiles changedFiles : changeSet) {
                    changedFiles.getFiles().forEach(file -> {
                        System.out.println("File changed: " + file.getFile().getName() )  ;
                        if (Files.exists(file.getFile().toPath())) {
                            fileService.processFIle();
                            try {


                                Path archivePath = Paths.get("/home/data/out/" , file.getFile().getName() );

                                Files.move(file.getFile().toPath(),archivePath,StandardCopyOption.REPLACE_EXISTING) ;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        };
                    });
                }
            }
        });
        watcher.start();
        System.out.println("File watcher started");
        return watcher;
    }
}
