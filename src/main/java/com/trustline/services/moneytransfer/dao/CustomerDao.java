package com.trustline.services.moneytransfer.dao;

import com.trustline.services.moneytransfer.dto.Customer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class CustomerDao {

    private static final Map<Long, Customer> customers =
            new ConcurrentHashMap<Long, Customer>(16);

    static {
        customers.put(1L, new Customer("Alice")); // port 8080
        customers.put(2L, new Customer("Bob")); // Port 8082
    }

    public Customer getCustomer(long id) {
        return customers.get(id);
    }

    public Customer getCustomerByName(String name) {
        Set<Long> keys = customers.keySet();

        for (Long key : keys) {
            if(customers.get(key).equals(name)) {
                return customers.get(key);
            }
        }
        return null;
    }

}
