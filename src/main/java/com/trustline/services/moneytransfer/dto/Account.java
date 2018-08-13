package com.trustline.services.moneytransfer.dto;

import com.google.common.util.concurrent.AtomicDouble;

public class Account {

    private final long id;
    private AtomicDouble balance;

    public Account(long id, AtomicDouble balance) {
        this.id = id;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public AtomicDouble getBalance() {
        return balance;
    }

    public void setBalance(AtomicDouble balance) {
        this.balance = balance;
    }
}
