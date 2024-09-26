package org.example.swappayment;
import org.example.swappayment.KafkaService.KafkaBatchControl;
import org.example.swappayment.KafkaService.KafkaProducer;
import org.example.swappayment.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwapController {

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private KafkaBatchControl kafkaBatchControl;

    @Autowired
    private JmsTemplate jmsTemplate;

    static final String payment = "PaymentId,Amount\n" +
            "1234567,129.99\n" +
            "1234568,33.60\n" +
            "1234567,129.99\n" +
            "1234568,33.60\n" +
            "1234567,129.99\n" +
            "1234568,33.60";

    @GetMapping("/sendKafka")
    public String sendMessage(@RequestParam("message") String message) {
        kafkaBatchControl.batchStart();
        kafkaProducer.sendMessage(message);
        kafkaBatchControl.batchEnd("one message");
        return "Message sent to Kafka topic";

    }

    @GetMapping("/sendMQ")
    String send(){
        try{
            jmsTemplate.convertAndSend(Constant.DESTINATION_NAME, Constant.HELLO_WORLD);
            return "OK";
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }
    @GetMapping("/payMQ")
    String pay(){
        try{
            jmsTemplate.convertAndSend(Constant.DESTINATION_NAME, payment);
            return "OK";
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }
//    @GetMapping("/recv")
//    String recv(){
//        System.out.println("/recv");
//        try{
//            return jmsTemplate.receiveAndConvert(Constant.DESTINATION_NAME).toString();
//        }catch(JmsException ex){
//            ex.printStackTrace();
//            return "FAIL";
//        }
//    }
}
