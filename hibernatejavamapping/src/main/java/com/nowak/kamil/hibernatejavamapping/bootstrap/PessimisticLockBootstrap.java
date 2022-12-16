package com.nowak.kamil.hibernatejavamapping.bootstrap;

import com.nowak.kamil.hibernatejavamapping.order.domain.Product;
import com.nowak.kamil.hibernatejavamapping.order.domain.ProductStatus;
import com.nowak.kamil.hibernatejavamapping.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PessimisticLockBootstrap implements CommandLineRunner {

    private final ProductService productService;


    @Override
    public void run(String... args) throws Exception {
        final var product = new Product();
        product.setDescription("aadsad");
        product.setProductStatus(ProductStatus.NEW);

        final var savedProduct = productService.saveProduct(product);

        final var savedProduct2 = productService.updateQOH(savedProduct.getId(), 25);
    }
}
