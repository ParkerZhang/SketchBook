package org.example.swappayment.DataService;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.swappayment.KafkaService.KafkaBatchControl;
import org.example.swappayment.KafkaService.KafkaDataException;
import org.example.swappayment.KafkaService.KafkaProducer;
import org.example.swappayment.PaymentService.Payment;
import org.example.swappayment.constant.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class DataService {
    final KafkaProducer producer;
    final KafkaBatchControl batchControl;
    final KafkaDataException dataException;

    public DataService(KafkaProducer producer, KafkaBatchControl batchControl, KafkaDataException dataException) {
        this.producer = producer;
        this.batchControl = batchControl;
        this.dataException = dataException;
    }

    public void processCSV(File file) {

        try {
            String csvContent = Files.readString(file.toPath());
            System.out.println(csvContent);

            process(csvContent);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    public void process(String string) {
        System.out.print("Data Service!");
        if (!isValidData(string)) {
            dataException.sendMessage(string);
            return;
        }
        try {
            CSVReader csvReader = new CSVReader(new StringReader(string));
            CsvToBean<Payment> csvToBean = new CsvToBeanBuilder<Payment>(csvReader).withType(Payment.class).build();

            List<Payment> objects = csvToBean.parse();
            csvReader.close();
            batchControl.batchStart();
            for (Payment o : objects) {

                if (o.getPaymentId() < 0)
                    dataException.sendMessage(String.format("Payment id is not valid(%d),record skipped, \n%s", o.getPaymentId(), o.toJson()));
                else
                    producer.sendMessage(o.toJson()); // TODO to add sendMessage(o), conflict KafkaTemplate with <String, String>
            }
            String s = String.format("processed %d record(s).", objects.size());

            batchControl.batchEnd(s);
            System.out.println(s);
        } catch (Exception e) {

            batchControl.batchEnd(Arrays.toString(e.getStackTrace()));
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }


    public boolean isValidData(String string) {
        if (string.startsWith(Constant.CSV_HEADER))
            return true;
        else
            return false;

    }
}
