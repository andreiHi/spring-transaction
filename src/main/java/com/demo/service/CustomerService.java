package com.demo.service;

import com.demo.domain.Customer;
import com.demo.domain.CustomerCreated;
import com.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Customer createCustomer(String name, String email) {
        final Customer customer = this.customerRepository.save(new Customer(name, email));
        eventPublisher.publishEvent(new CustomerCreated(customer.getId()));
        return customer;
    }
}
