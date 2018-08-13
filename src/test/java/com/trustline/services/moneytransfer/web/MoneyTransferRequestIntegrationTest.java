package com.trustline.services.moneytransfer.web;

import com.trustline.services.moneytransfer.TrustlineApplication;
import com.trustline.services.moneytransfer.dto.MoneyTransferRequest;
import com.trustline.services.moneytransfer.dto.MoneyTransferResponse;
import com.trustline.services.moneytransfer.dto.TransactionStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.web.client.RestTemplate;


public class MoneyTransferRequestIntegrationTest {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void transfersTest() {

        // Start Alice'e server at port 8080
        SpringApplicationBuilder aliceServer = new SpringApplicationBuilder(TrustlineApplication.class)
                .properties("server.port=8080");
        aliceServer.run();

        // Start Bob's server at port 8082
        SpringApplicationBuilder bobServer = new SpringApplicationBuilder(TrustlineApplication.class)
                .properties("server.port=8082");
        bobServer.run();

        // Sending money 10 Euros from Alice/id-1/port-8080 to Bob/id-2/port-8082
        MoneyTransferRequest transfer = new MoneyTransferRequest(10, "EUR", 1, 2);
        MoneyTransferResponse response = restTemplate.postForObject("http://localhost:8080/transfers/outgoing", transfer, MoneyTransferResponse.class);
        Assert.assertEquals(TransactionStatus.PROCESSED, response.getStatus());

        // Sending money 50 Euros from Bob/id-2/port-8082 to Alice/id-1/port-8080
        transfer = new MoneyTransferRequest(60, "EUR", 2, 1);
        response = restTemplate.postForObject("http://localhost:8082/transfers/outgoing", transfer, MoneyTransferResponse.class);
        Assert.assertEquals(TransactionStatus.PROCESSED, response.getStatus());
    }
}
