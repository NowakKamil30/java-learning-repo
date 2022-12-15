package com.nowak.kamil.hibernatejavamapping.repository;

import com.nowak.kamil.hibernatejavamapping.domain.Product;
import com.nowak.kamil.hibernatejavamapping.domain.ProductStatus;
import com.nowak.kamil.hibernatejavamapping.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {ProductService.class})
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Test
    void testGetCategory() {
        final var saved = productRepository.save(Product.builder()
                        .description("kaka")
                        .productStatus(ProductStatus.NEW)
                .build());

        final var found = productRepository.findByDescription("kaka");

        assertNotNull(found);
        assertEquals(saved, found.get());


    }

    @Test
    void check() {
        final var product = new Product();
        product.setDescription("lalala");
        product.setProductStatus(ProductStatus.NEW);
        final var saved = productRepository.save(product);
        saved.setProductStatus(ProductStatus.IN_STOCK);
        final var updated = productRepository.save(saved);

        assertEquals(saved.getId(), updated.getId());
        assertEquals(updated.getProductStatus(), ProductStatus.IN_STOCK);
    }

    @Test
    void addAndUpdateProduct() {
        final var product = new Product();
        product.setDescription("aadsad");
        product.setProductStatus(ProductStatus.NEW);

        final var savedProduct = productService.saveProduct(product);

        final var savedProduct2 = productService.updateQOH(savedProduct.getId(), 25);

        assertEquals(25, savedProduct2.getQuantityOnHand());
    }
}