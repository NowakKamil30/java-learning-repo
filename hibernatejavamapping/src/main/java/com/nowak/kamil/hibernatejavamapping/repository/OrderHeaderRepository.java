package com.nowak.kamil.hibernatejavamapping.repository;

import com.nowak.kamil.hibernatejavamapping.domain.Customer;
import com.nowak.kamil.hibernatejavamapping.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.Set;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
    Set<OrderHeader> findAllByCustomer(Customer customer);
}
