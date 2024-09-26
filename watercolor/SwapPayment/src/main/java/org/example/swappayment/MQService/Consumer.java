package org.example.swappayment.MQService;


import org.example.swappayment.DataService.DataService;
import org.example.swappayment.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Consumer{
    private static Logger logger = LoggerFactory.getLogger(Consumer.class);
    @Autowired

    private final DataService dataService ;

    private static int i =0;

    public Consumer(DataService dataService) {
        this.dataService = dataService;
    }

    @JmsListener(destination = "${mq.DESTINATION_NAME}", containerFactory = "myFactory")
    public void receiveMessage(String message) {

        if (message.equals(Constant.LUBB_DUPP)) {
            logger.info("{} Original Message Received from Queue - Heard Heartbeat :: \n{}", ++i, message);

            return;
        }

        if (message.equals(Constant.HELLO_WORLD)) {
            logger.info("{} Original Message Received from Queue - Greeting :: \n{}", ++i, message);

            return;
        }
        logger.info("{} Original Message Received  from Queue :: \n{}", ++i, message);
        try {
            dataService.process(message);
        } catch (Exception e) { logger.debug("Error process data {} \n {} ",message, e.getStackTrace()); };
    }
}