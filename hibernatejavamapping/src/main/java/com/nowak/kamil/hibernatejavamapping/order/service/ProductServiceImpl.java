package com.nowak.kamil.hibernatejavamapping.order.service;

import com.nowak.kamil.hibernatejavamapping.order.domain.Product;
import com.nowak.kamil.hibernatejavamapping.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(final Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    @Transactional
    public Product updateQOH(Long id, Integer quantityOnHand) {
        final var product = productRepository.findById(id)
                .orElseThrow();

        product.setQuantityOnHand(quantityOnHand);
        return productRepository.saveAndFlush(product);
    }
}
