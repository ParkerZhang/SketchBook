package org.example.swappayment.KafkaService;

import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaDataException {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    private static final String TOPIC = "my_topic.control";

    public void sendMessage(String message) {
        String s = String.format("Bad data: %\n%s",message);
        System.out.println(s);
        kafkaTemplate.send(TOPIC,s);

    }

}
