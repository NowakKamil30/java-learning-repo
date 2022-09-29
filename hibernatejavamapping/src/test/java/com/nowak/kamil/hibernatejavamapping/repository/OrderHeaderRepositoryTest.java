package com.nowak.kamil.hibernatejavamapping.repository;

import com.nowak.kamil.hibernatejavamapping.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderApprovalRepository orderApprovalRepository;

    Product product;

    Customer customer;

    @BeforeEach
    void setUp() {
        product = productRepository.saveAndFlush(Product.builder()
                        .productStatus(ProductStatus.NEW)
                        .description("desc")
                        .build());

        customer = customerRepository.saveAndFlush(Customer.builder()
                .customerName("name")
                .address(new Address())
                .build());
    }

    @Test
    void orphanRemovalHowIsWorks() {
        final var approval = OrderApproval.builder().approvedBy("lala").build();
        final var build = OrderHeader.builder()
                .customer(customer)
                .shippingAddress(Address.builder()
                        .address("shipping")
                        .city("shipping")
                        .state("shipping")
                        .zipCode("shipping")
                        .build())
                .billToAddress(Address.builder()
                        .address("bill")
                        .city("bill")
                        .state("bill")
                        .zipCode("bill")
                        .build())
                .orderApproval(approval)
                .orderStatus(OrderStatus.NEW)
                .build();
        final var orderHeader = orderHeaderRepository.saveAndFlush(build);
        assertNotNull(orderHeader);
        assertNotNull(orderHeader.getId());
        assertNotNull(orderHeader.getOrderApproval());
        assertNotNull(orderHeader.getOrderApproval().getId());
        final var orderApproval = orderHeader.getOrderApproval();
        orderHeader.setOrderApproval(new OrderApproval());
        orderHeaderRepository.saveAndFlush(orderHeader);
        final var byId = orderApprovalRepository.findById(orderApproval.getId());

        assertTrue(byId.isEmpty());
    }

    @Test
    void shouldSaveObject() {
        final var build = OrderHeader.builder()
                .customer(customer)
                .shippingAddress(Address.builder()
                        .address("shipping")
                        .city("shipping")
                        .state("shipping")
                        .zipCode("shipping")
                        .build())
                .billToAddress(Address.builder()
                        .address("bill")
                        .city("bill")
                        .state("bill")
                        .zipCode("bill")
                        .build())
                .orderStatus(OrderStatus.NEW)
                .build();
        final var set = new HashSet<OrderHeader>();
        set.add(build);
        customer.setOrderHeaders(set);
        final var approval = OrderApproval.builder().approvedBy("lala").build();
        build.setOrderApproval(approval);
        final var savedOrderHeader = orderHeaderRepository.save(build);

        orderHeaderRepository.flush();

        final var byId = orderHeaderRepository.findById(savedOrderHeader.getId());

        assertNotNull(byId.get());
        assertEquals(savedOrderHeader, byId.get());
        assertNotNull(byId.get().getCustomer());
        assertNotNull(byId.get().getCustomer().getId());
        assertNotNull(byId.get().getOrderApproval());
        assertNotNull(byId.get().getOrderApproval().getId());
    }

    @Test
    void testSaveOrderWithLine() {
        final var line = OrderLine.builder()
                .quantityOrdered(5)
                .product(product)
                .build();
        final var line2 = OrderLine.builder()
                .quantityOrdered(10)
                .product(product)
                .build();
        final var orderHeader = OrderHeader.builder()
                .shippingAddress(Address.builder()
                        .address("shipping")
                        .city("shipping")
                        .state("shipping")
                        .zipCode("shipping")
                        .build())
                .billToAddress(Address.builder()
                        .address("bill")
                        .city("bill")
                        .state("bill")
                        .zipCode("bill")
                        .build())
                .orderStatus(OrderStatus.NEW)
                .orderLines(Set.of(
                        line,
                        line2
                ))
                .build();

        line.setOrderHeader(orderHeader);
        line2.setOrderHeader(orderHeader);

        final var savedOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderLines());
        assertEquals(savedOrder.getOrderLines().size(), 2);
        assertNotNull(savedOrder.getOrderLines().stream().findFirst().get());
        assertNotNull(savedOrder.getOrderLines().stream().findFirst().get().getId());
        assertNotNull(savedOrder.getOrderLines().stream().findFirst().get().getProduct().getId());
    }

    @Test
    void testDeleteCascade() {

        final var orderHeader = new OrderHeader();
        final var Customer = new Customer();
        customer.setCustomerName("lalala");
        orderHeader.setCustomer(customerRepository.save(customer));

        final var orderLine = new OrderLine();
        orderLine.setQuantityOrdered(10);
        orderLine.setProduct(product);

        final var orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("me");
        orderHeader.setOrderApproval(orderApproval);

        orderHeader.addOrderLine(orderLine);
        final var savedOrder = orderHeaderRepository.saveAndFlush(orderHeader);

        orderHeaderRepository.deleteById(savedOrder.getId());
        orderHeaderRepository.flush();

        final var fetchedOrder = orderHeaderRepository.findById(savedOrder.getId());

        assertTrue(fetchedOrder.isEmpty());

    }

}