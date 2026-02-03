package org.example.model;

import java.math.BigDecimal;

public class AchTransaction {
    private String transactionId;
    private String originatingDfi;
    private String receivingDfi;
    private BigDecimal amount;
    private String effectiveEntryDate;

    public AchTransaction() {
    }

    public AchTransaction(String transactionId, String originatingDfi, String receivingDfi, 
                          BigDecimal amount, String effectiveEntryDate) {
        this.transactionId = transactionId;
        this.originatingDfi = originatingDfi;
        this.receivingDfi = receivingDfi;
        this.amount = amount;
        this.effectiveEntryDate = effectiveEntryDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOriginatingDfi() {
        return originatingDfi;
    }

    public void setOriginatingDfi(String originatingDfi) {
        this.originatingDfi = originatingDfi;
    }

    public String getReceivingDfi() {
        return receivingDfi;
    }

    public void setReceivingDfi(String receivingDfi) {
        this.receivingDfi = receivingDfi;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getEffectiveEntryDate() {
        return effectiveEntryDate;
    }

    public void setEffectiveEntryDate(String effectiveEntryDate) {
        this.effectiveEntryDate = effectiveEntryDate;
    }

    public String getStatus() {
        if (amount != null && amount.compareTo(new BigDecimal("25000")) > 0) {
            return "HIGH VALUE / REQUIRES APPROVAL";
        }
        return "STANDARD / NORMAL";
    }
}
