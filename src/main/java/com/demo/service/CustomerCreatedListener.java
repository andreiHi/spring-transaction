package com.demo.service;

import com.demo.domain.Customer;
import com.demo.domain.CustomerCreated;
import com.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CustomerCreatedListener {
    private final CustomerRepository customerRepository;
    private final TokenGenerator tokenGenerator;

    @TransactionalEventListener
    @Async
    //@Transactional(propagation = Propagation.REQUIRES_NEW) for first test withOut async
    public void handle(CustomerCreated event) {
        final Customer customer = this.customerRepository.findById(event.getCustomerId()).get();
        tokenGenerator.generateToken(customer);
    }
}
