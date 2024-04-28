package scketch.anatomy.restapi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UNIQUE_TRANSACTION_ID")
public class UniqueTransactionId {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "FUNDID", nullable = false)
    private Long fundid;

    @Column(name = "SWAP_ID", nullable = false)
    private Long swapId;

    @Column(name = "DATE_TIME")
    private String dateTime;

    @Column(name = "EVENT")
    private String event;

    @Column(name = "UNIQUE_PRODUCT_ID")
    private String uniqueProductId;

    @Column(name = "UNIQUE_TRANSACTION_ID")
    private String uniqueTransactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFundid() {
        return fundid;
    }

    public void setFundid(Long fundid) {
        this.fundid = fundid;
    }

    public Long getSwapId() {
        return swapId;
    }

    public void setSwapId(Long swapId) {
        this.swapId = swapId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUniqueProductId() {
        return uniqueProductId;
    }

    public void setUniqueProductId(String uniqueProductId) {
        this.uniqueProductId = uniqueProductId;
    }

    public String getUniqueTransactionId() {
        return uniqueTransactionId;
    }

    public void setUniqueTransactionId(String uniqueTransactionId) {
        this.uniqueTransactionId = uniqueTransactionId;
    }

}