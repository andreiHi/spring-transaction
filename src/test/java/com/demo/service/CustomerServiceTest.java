package com.demo.service;

import com.demo.domain.Customer;
import com.demo.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void shouldCreateAndPersistCustomer() {
        Customer customer = customerService.createCustomer("Andrei", "andrei@gmail.com");
        Customer persistCustomer = customerRepository.findById(customer.getId()).get();
        assertEquals("Andrei", persistCustomer.getName());
        assertEquals("andrei@gmail.com", persistCustomer.getEmail());
        assertTrue(persistCustomer.hasToken());
    }

    @Test
    public void shouldCreateAndPersistCustomerWithAsync() {
        Customer customer = customerService.createCustomer("Andrei", "andrei@gmail.com");

        await().atMost(5, TimeUnit.SECONDS)
                .until(() -> customerHasToken(customer.getId()));

    }

    private boolean customerHasToken(Long id) {
        Customer persistCustomer = customerRepository.findById(id).get();
        return persistCustomer.hasToken();
    }
}
