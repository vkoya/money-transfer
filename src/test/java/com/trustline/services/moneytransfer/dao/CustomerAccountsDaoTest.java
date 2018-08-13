package com.trustline.services.moneytransfer.dao;

import com.google.common.util.concurrent.AtomicDouble;
import com.trustline.services.moneytransfer.dto.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


public class CustomerAccountsDaoTest {

    @InjectMocks
    private CustomerAccountsDao customerAccountsDao;

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddAccountToCustomer() {
        Account account = customerAccountsDao.addAccountToCustomer(3L, new Account(3L, new AtomicDouble(24.0)));
        Assert.assertNotNull(account);
        Assert.assertEquals(3L, account.getId());
        Assert.assertEquals(24.0, account.getBalance().doubleValue(), 0.000001);
    }
}
