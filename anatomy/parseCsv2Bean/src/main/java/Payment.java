
import com.opencsv.bean.CsvBindByName;
public class Payment {
    @CsvBindByName
    public String  paymentId;
    @CsvBindByName
    public Double  amount;
}
