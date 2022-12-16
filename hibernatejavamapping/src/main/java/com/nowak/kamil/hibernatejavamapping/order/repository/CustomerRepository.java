package com.nowak.kamil.hibernatejavamapping.order.repository;

import com.nowak.kamil.hibernatejavamapping.order.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByCustomerNameIgnoreCase(String customerName);
}
