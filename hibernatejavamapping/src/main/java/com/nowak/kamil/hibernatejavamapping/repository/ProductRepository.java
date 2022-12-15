package com.nowak.kamil.hibernatejavamapping.repository;

import com.nowak.kamil.hibernatejavamapping.domain.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByDescription(final String description);

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE) //select for update
    Optional<Product> findById(Long aLong);
}
