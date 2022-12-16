package com.nowak.kamil.hibernatejavamapping.order.service;

import com.nowak.kamil.hibernatejavamapping.order.domain.Product;

public interface ProductService {

    Product saveProduct(Product product);

    Product updateQOH(Long id, Integer quantityOnHand);
}
