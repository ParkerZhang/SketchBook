package com.example.filewatcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Scheduler1 {
    protected final Log logger = LogFactory.getLog(getClass());


    static private int i = 0;

    Scheduler1( ) {
         System.out.println("Scheduler1 Started.");
    }

    @Scheduled(initialDelay = 2 * Constant.MINUTE, fixedRate = 10  * Constant.MINUTE)
    public void run() {
        String msg = "Sending messages in cycle :" + i++;

        logger.info("");
        logger.info( this.getClass().getSimpleName());
        logger.info(msg);
        logger.info("Performing put");

    }
}