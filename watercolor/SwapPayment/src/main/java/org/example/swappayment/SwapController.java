package org.example.swappayment;
import org.example.swappayment.KafkaService.KafkaBatchControl;
import org.example.swappayment.KafkaService.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwapController {

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private KafkaBatchControl kafkaBatchControl;

    @GetMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        kafkaBatchControl.batchStart();
        kafkaProducer.sendMessage(message);
        kafkaBatchControl.batchEnd("one message");
        return "Message sent to Kafka topic";

    }
}
