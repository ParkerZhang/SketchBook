package com.example.mq_spring;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// When enabled this component ensures that the application keeps running, until interrupted.
@Component
//@EnableScheduling
public class HeartBeat {
    protected final Log logger = LogFactory.getLog(getClass());

    static private int i = 0;

    @Scheduled(initialDelay = 5 * Constant.SECOND, fixedRate = 5*Constant.SECOND)
    public void run() throws InterruptedException {
        String msg = "About to perform operation " + i++;
        logger.info(msg);
        Thread.sleep(10000);
    }
}