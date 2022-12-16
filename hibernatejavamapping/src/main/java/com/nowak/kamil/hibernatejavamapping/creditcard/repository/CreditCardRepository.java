package com.nowak.kamil.hibernatejavamapping.creditcard.repository;

import com.nowak.kamil.hibernatejavamapping.creditcard.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
