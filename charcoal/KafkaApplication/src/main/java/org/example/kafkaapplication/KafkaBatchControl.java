package org.example.kafkaapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaBatchControl {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "my_topic.control";

    public void batchStart() {
        kafkaTemplate.send(TOPIC, TimeService.getLocalTime(), "Batch Start");
    }

    public void batchEnd(String totalMessages ) {
        kafkaTemplate.send(TOPIC, TimeService.getLocalTime(), "Batch End : " +totalMessages);
    }
}
