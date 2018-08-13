package com.trustline.services.moneytransfer.dto;

public class MoneyTransferResponse {

    private TransactionStatus status;

    public MoneyTransferResponse() {
    }

    public MoneyTransferResponse(TransactionStatus status) {
        this.status = status;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MoneyTransferResponse{" +
                "status=" + status +
                '}';
    }
}
