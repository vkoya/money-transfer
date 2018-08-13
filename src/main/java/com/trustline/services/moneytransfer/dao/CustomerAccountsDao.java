package com.trustline.services.moneytransfer.dao;

import com.google.common.util.concurrent.AtomicDouble;
import com.trustline.services.moneytransfer.dto.Account;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data Access layer
 */
@Service
public class CustomerAccountsDao {

    private static final Map<Long, Account> customerAccounts =
            new ConcurrentHashMap<Long, Account>(16);

    private static final AtomicDouble INITIAL_BALANCE = new AtomicDouble(0);

    static {
        customerAccounts.put(1L, new Account(1L, INITIAL_BALANCE));
        customerAccounts.put(2L, new Account(2L, INITIAL_BALANCE));
    }

    public Account addAccountToCustomer(long customerId, Account account) {
        customerAccounts.put(customerId, account);
        return customerAccounts.get(customerId);
    }

    public Account getCustomerAccount(long customerId) {
        return customerAccounts.get(customerId);
    }
}
