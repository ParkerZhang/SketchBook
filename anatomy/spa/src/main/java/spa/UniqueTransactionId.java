package spa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class UniqueTransactionId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long swapId;
    private long fundid;
    private String event;
    private String dateTime;
    private String uniqueTransactionId;
    private String uniqueProductId;

    public long getId() {
        return id;
    }



    public long getSwapId() {
        return swapId;
    }

    public long getFundid() {
        return fundid;
    }

    public String getEvent() {
        return event;
    }

    public String getUniqueTransactionId() {
        return uniqueTransactionId;
    }

    public String getUniqueProductId() {
        return uniqueProductId;
    }

    protected UniqueTransactionId() {}

    public UniqueTransactionId(long swapId, long fundId, String event, String uniqueTransactionId, String uniqueProductId) {

        this.swapId = swapId;
        this.fundid = fundId;
        this.event = event;
        this.uniqueTransactionId = uniqueTransactionId;
        this.uniqueProductId = uniqueProductId;

    }
    @Override
    public String toString() {
        return String.format("%d,%d,%s,%s,%s", swapId,fundid,event,uniqueTransactionId,uniqueProductId);
    }

}
