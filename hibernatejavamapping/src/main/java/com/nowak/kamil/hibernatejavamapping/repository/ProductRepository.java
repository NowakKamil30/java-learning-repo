package com.nowak.kamil.hibernatejavamapping.repository;

import com.nowak.kamil.hibernatejavamapping.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByDescription(final String description);
}
