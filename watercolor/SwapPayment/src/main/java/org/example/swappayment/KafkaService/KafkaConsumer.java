package org.example.swappayment.KafkaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @KafkaListener(topics = "my_topic", groupId = "my-group")
    public void consume(String message) {

        logger.info("Consumed message: {} " , message);
    }
}
