package com.nowak.kamil.hibernatejavamapping.order.repository;

import com.nowak.kamil.hibernatejavamapping.order.domain.Customer;
import com.nowak.kamil.hibernatejavamapping.order.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
    Set<OrderHeader> findAllByCustomer(Customer customer);
}
