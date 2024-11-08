package org.example.springshutdown;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class GracefulShutdownConfig {
//    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> webServerFactoryCustomizer() {
//        return factory -> factory.addConnectorCustomizers(connector -> connector.setAsyncTimeout(30000));
//    }
//
////    @EventListener
////    public void onContextClosed(ContextClosedEvent event) {
//////        try {
////            System.out.println("Shutting down...");
//////            executorService.shutdown();
//////            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
//////                executorService.shutdownNow();
//////            }
//////        } catch (InterruptedException e) {
//////            executorService.shutdownNow();
//////        }
////    }
//}
