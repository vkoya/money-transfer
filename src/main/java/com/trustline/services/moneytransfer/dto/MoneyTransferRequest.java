package com.trustline.services.moneytransfer.dto;

public class MoneyTransferRequest {

    private double amount;
    private String currency;
    private long debitedUserId;
    private long creditedUserId;

    public MoneyTransferRequest(double amount, String currency, long debitedUserId, long creditedUserId) {
        this.amount = amount;
        this.currency = currency;
        this.debitedUserId = debitedUserId;
        this.creditedUserId = creditedUserId;
    }

    public MoneyTransferRequest() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getDebitedUserId() {
        return debitedUserId;
    }

    public void setDebitedUserId(long debitedUserId) {
        this.debitedUserId = debitedUserId;
    }

    public long getCreditedUserId() {
        return creditedUserId;
    }

    public void setCreditedUserId(long creditedUserId) {
        this.creditedUserId = creditedUserId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MoneyTransferRequest{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", debitedUserId=" + debitedUserId +
                ", creditedUserId=" + creditedUserId +
                '}';
    }
}
