package com.example.MQ.consumer;

import com.example.MQ.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

//@Component
public class MessageConsumer {
    private static Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private static int i =0;

    @JmsListener(destination = "${mq.DESTINATION_NAME}", containerFactory = "myFactory")
    public void receiveMessage(String message) {
        logger.info("{} Original Message Received  from Queue :: \n{}", ++i, message);
    }
}
