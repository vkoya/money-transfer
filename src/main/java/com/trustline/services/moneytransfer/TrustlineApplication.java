package com.trustline.services.moneytransfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrustlineApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrustlineApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TrustlineApplication.class, args);
        LOGGER.info("Welcome to the Trustline");
        LOGGER.info("Trustline balance is: 0");
    }
}
