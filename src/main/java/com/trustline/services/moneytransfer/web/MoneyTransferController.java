package com.trustline.services.moneytransfer.web;

import com.trustline.services.moneytransfer.dto.MoneyTransferResponse;
import com.trustline.services.moneytransfer.dto.MoneyTransferRequest;
import com.trustline.services.moneytransfer.service.MoneyTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MoneyTransferController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyTransferController.class);

    @Autowired
    private MoneyTransferService moneyTransferService;

    @RequestMapping(value = "transfers/outgoing", method = RequestMethod.POST)
    public MoneyTransferResponse outgoingTransfer(@RequestBody MoneyTransferRequest moneyTransferRequestDTO,
                                                  HttpServletRequest request) throws Exception {
        LOGGER.debug("Received request for outgoing transfer: ", moneyTransferRequestDTO);
        //TODO: add validations to check amount to be greater than 0 and validations for other request elements
        MoneyTransferResponse moneyTransferResponse = moneyTransferService.outgoingTransfer(moneyTransferRequestDTO,
                                                                                request.getServerPort());
        //TODO: Response Validations
        return moneyTransferResponse;
    }

    // TODO: If there is central DB, this call is not needed
    @RequestMapping(value = "transfers/incoming", method = RequestMethod.POST)
    public MoneyTransferResponse incomingTransfer(@RequestBody MoneyTransferRequest moneyTransferRequestDTO) {
        LOGGER.debug("Received request for incoming transfer: ", moneyTransferRequestDTO);
        return moneyTransferService.incomingTransfer(moneyTransferRequestDTO);
    }
}
