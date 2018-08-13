package com.trustline.services.moneytransfer.service;

import com.google.common.util.concurrent.AtomicDouble;
import com.trustline.services.moneytransfer.dao.CustomerAccountsDao;
import com.trustline.services.moneytransfer.dto.Account;
import com.trustline.services.moneytransfer.dto.MoneyTransferRequest;
import com.trustline.services.moneytransfer.dto.MoneyTransferResponse;
import com.trustline.services.moneytransfer.dto.TransactionStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


public class MoneyTransferServiceTest {

    @InjectMocks
    private MoneyTransferService moneyTransferService;

    @Mock
    private CustomerAccountsDao customerAccountsDao;

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIncomingTransfer() {

        Account account = new Account(1L, new AtomicDouble(55.0));
        when(customerAccountsDao.getCustomerAccount(anyLong())).thenReturn(account);
        MoneyTransferResponse moneyTransferResponse = moneyTransferService.incomingTransfer(buildMoneyTransferRequest());
        Assert.assertNotNull(moneyTransferResponse);
        Assert.assertEquals(TransactionStatus.PENDING, moneyTransferResponse.getStatus());
    }

    @Test(expected = Exception.class)
    public void testOutgoingTransfer() throws Exception {
        moneyTransferService.outgoingTransfer(buildMoneyTransferRequest(), 8080);
    }

    private MoneyTransferRequest buildMoneyTransferRequest() {
        MoneyTransferRequest moneyTransferRequest = new MoneyTransferRequest();
        moneyTransferRequest.setCreditedUserId(1L);
        moneyTransferRequest.setAmount(4.0);

        return moneyTransferRequest;
    }
}
