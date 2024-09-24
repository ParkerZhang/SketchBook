package org.example.swappayment.DataService;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.swappayment.KafkaService.KafkaBatchControl;
import org.example.swappayment.KafkaService.KafkaProducer;
import org.example.swappayment.PaymentService.Payment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

@Component
public class DataService {
    final KafkaProducer producer;
    final KafkaBatchControl batchControl;

    public DataService(KafkaProducer producer, KafkaBatchControl batchControl) {
        this.producer = producer;
        this.batchControl = batchControl;
    }

    public void processCSV(File file) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(file));
            CsvToBean<Payment> csvToBean = new CsvToBeanBuilder<Payment>(csvReader).withType(Payment.class).build();

            List<Payment> objects = csvToBean.parse();
            csvReader.close();
            batchControl.batchStart();
            for (Payment o : objects) {
                System.out.printf("Payment Id: %s\tAmount: %f\n", o.getPaymentId(), o.getAmount());
                producer.sendMessage(o.toJson()); // TODO to add sendMessage(o), conflict KafkaTemplate with <String, String>
            }
            String s = String.format( "%s processed, total %d record(s).", file.getAbsolutePath() ,objects.size());

            batchControl.batchEnd(s);
            System.out.println(s);
        } catch (Exception e) {
            batchControl.batchEnd(Arrays.toString(e.getStackTrace()));
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
