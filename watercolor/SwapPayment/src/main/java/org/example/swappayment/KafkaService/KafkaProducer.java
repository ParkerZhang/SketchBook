package org.example.swappayment.KafkaService;

import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    private static final String TOPIC = "my_topic";

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }

}
