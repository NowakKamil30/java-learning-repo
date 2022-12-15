package com.nowak.kamil.hibernatejavamapping.bootstrap;

import com.nowak.kamil.hibernatejavamapping.domain.Customer;
import com.nowak.kamil.hibernatejavamapping.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OptimisticLockBootstrap implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        final var customer = customerRepository.save(Customer.builder()
                .customerName("Name")
                .build());

        log.info("customer version: {}", customer.getVersion());

        customer.setCustomerName("2");

        customer.setCustomerName("this is it");

        customer.setPhone("999888777");

        final var savedCustomer = customerRepository.save(customer);

        savedCustomer.setEmail("21321@o2.pl");

        customerRepository.save(savedCustomer);
    }
}
