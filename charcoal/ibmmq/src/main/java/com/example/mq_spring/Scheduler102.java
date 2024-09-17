package com.example.mq_spring;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Scheduler102 {
    protected final Log logger = LogFactory.getLog(getClass());

    private final SendMessageService102 service;
    static private int i = 0;

    Scheduler102(SendMessageService102 service) {
        this.service = service;
    }

    @Scheduled(initialDelay = 2 * Constant.SECOND, fixedRate = 10  * Constant.SECOND)
    public void run() {
        String msg = "Sending messages in cycle :" + i++;

        logger.info("");
        logger.info( this.getClass().getSimpleName());
        logger.info(msg);
        logger.info("Performing put");
        service.put(msg);
        logger.info("Performing publish");
        service.publish(msg);
    }
}