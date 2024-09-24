
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main {
    @SuppressWarnings({"rawtypes","unchecked"})
    public static void main(String[] args) throws IOException {
        URL fileUrl = Main.class.getClassLoader().getResource("payment.csv");
        CSVReader csvReader= new CSVReader(new FileReader(fileUrl.getFile()));
        CsvToBean <Payment> csvToBean = new CsvToBeanBuilder<Payment>(csvReader).withType(Payment.class).build();

        List <Payment> objects = csvToBean.parse();
        csvReader.close();
        for (Payment o:objects)
            System.out.printf("Payment Id: %s\tAmount: %f\n",o.paymentId,o.amount);
        System.out.println("Hello world!");
    }

}