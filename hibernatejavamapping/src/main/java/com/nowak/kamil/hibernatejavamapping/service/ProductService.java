package com.nowak.kamil.hibernatejavamapping.service;

import com.nowak.kamil.hibernatejavamapping.domain.Product;

public interface ProductService {

    Product saveProduct(Product product);

    Product updateQOH(Long id, Integer quantityOnHand);
}
