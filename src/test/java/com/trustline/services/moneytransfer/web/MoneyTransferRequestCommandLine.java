package com.trustline.services.moneytransfer.web;

import com.trustline.services.moneytransfer.dto.MoneyTransferRequest;
import com.trustline.services.moneytransfer.dto.MoneyTransferResponse;
import com.trustline.services.moneytransfer.dto.TransactionStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;


public class MoneyTransferRequestCommandLine {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void transfersTest() {

        /*
        1. Start Alice'e server at port 8080 in command line
        $ ./mvnw.sh spring-boot:run

        2. Start Bob's server at port 8082 in command line
        $ ./mvnw.sh spring-boot:run -Dserver.port=8082
        */

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
