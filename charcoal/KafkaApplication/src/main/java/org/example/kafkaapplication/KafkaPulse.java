package org.example.kafkaapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@EnableScheduling
public class KafkaPulse {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "my_topic.control";
    @Scheduled(fixedRate = 5000)
    public void HeartBeat( ) {

        String beat = String.format(  "{\"heartbeat\": \"%s\" , \"localTime\": \"%s\"}", TimeService.getUTC(),TimeService.getLocalTime());
//        System.out.println(beat);
        kafkaTemplate.send(TOPIC,TimeService.getLocalTime(),beat );
    }
}
