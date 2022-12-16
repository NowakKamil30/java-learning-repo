package com.nowak.kamil.hibernatejavamapping.order.repository;

import com.nowak.kamil.hibernatejavamapping.order.domain.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
}
