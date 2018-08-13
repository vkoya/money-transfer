package com.trustline.services.moneytransfer.service;

import com.google.common.util.concurrent.AtomicDouble;
import com.trustline.services.moneytransfer.dao.CustomerAccountsDao;
import com.trustline.services.moneytransfer.dao.CustomerDao;
import com.trustline.services.moneytransfer.dto.Account;
import com.trustline.services.moneytransfer.dto.Customer;
import com.trustline.services.moneytransfer.dto.MoneyTransferRequest;
import com.trustline.services.moneytransfer.dto.MoneyTransferResponse;
import com.trustline.services.moneytransfer.dto.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class MoneyTransferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyTransferService.class);

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerAccountsDao customerAccountsDao;

    @Value("${host}")
    private String host;

    @Value("${alice.server.port}")
    private int aliceServerPort;

    @Value("${bob.server.port}")
    private int bobServerPort;

    @Value("${incoming.transfers.path}")
    private String incomingTransfersPath;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Business layer for transfer service
     *
     * @param request
     * @param serverPort
     * @return
     * @throws Exception
     */
    public MoneyTransferResponse outgoingTransfer(MoneyTransferRequest request, int serverPort) throws Exception {

        MoneyTransferResponse response;

        String incomingTransferUri = destinationUri(serverPort);

        if (StringUtils.isEmpty(incomingTransferUri)) {
            throw new Exception("Please configure server ports");
        }

        Customer creditedCustomer = customerDao.getCustomer(request.getCreditedUserId());
        Account debitedCustomerAccount = customerAccountsDao.getCustomerAccount(request.getDebitedUserId());

        if (creditedCustomer.equals(customerDao.getCustomerByName("Bob")) && serverPort == bobServerPort) {
            throw new Exception("Please correct request. " +
                    "Bob's data is only on the server running at port: " + bobServerPort);
        }

        if (creditedCustomer.equals(customerDao.getCustomerByName("Alice")) && serverPort == aliceServerPort) {
            throw new Exception("Please correct request. Alice's data is only on the server " +
                    "running at port: " + aliceServerPort);
        }

        LOGGER.info("Paying " + request.getAmount() + " to " + creditedCustomer.getName() + "...");

        // TODO/start - Ideally in central DB, this block should be transactional
        // Cross banking transfers should have several status associated with a transaction,
        // so can avoid double spending issue

        response = restTemplate.postForObject(incomingTransferUri, request, MoneyTransferResponse.class);

        AtomicDouble updatedBalance = new AtomicDouble(debitedCustomerAccount.getBalance().doubleValue() - request.getAmount());
        debitedCustomerAccount.setBalance(updatedBalance);
        // TODO/end - transaction

        response.setStatus(TransactionStatus.PROCESSED);
        LOGGER.info("Trustline balance is: " + customerAccountsDao.getCustomerAccount(request.getDebitedUserId()).getBalance());

        return response;
    }

    public MoneyTransferResponse incomingTransfer(MoneyTransferRequest request) {
        Account creditedCustomerAccount = customerAccountsDao.getCustomerAccount(request.getCreditedUserId());
        AtomicDouble updatedBalance = new AtomicDouble(creditedCustomerAccount.getBalance().addAndGet(request.getAmount()));
        creditedCustomerAccount.setBalance(updatedBalance);

        LOGGER.info("You were paid " + request.getAmount() + "!");

        LOGGER.info("Trustline balance is: " + customerAccountsDao.getCustomerAccount(request.getCreditedUserId()).getBalance());
        return new MoneyTransferResponse(TransactionStatus.PENDING);
    }

    private String destinationUri(int serverPort) {

        StringBuilder destinationUri = new StringBuilder();

        if (serverPort == aliceServerPort) {
            destinationUri.append(host).append(":").append(bobServerPort).append(incomingTransfersPath);
        } else if (serverPort == bobServerPort) {
            destinationUri.append(host).append(":").append(aliceServerPort).append(incomingTransfersPath);
        }

        return destinationUri.toString();
    }
}
