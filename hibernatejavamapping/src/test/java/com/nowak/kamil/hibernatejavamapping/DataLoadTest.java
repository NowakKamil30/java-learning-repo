package com.nowak.kamil.hibernatejavamapping;

import com.nowak.kamil.hibernatejavamapping.domain.*;
import com.nowak.kamil.hibernatejavamapping.repository.CustomerRepository;
import com.nowak.kamil.hibernatejavamapping.repository.OrderHeaderRepository;
import com.nowak.kamil.hibernatejavamapping.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jt on 5/28/22.
 */
@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataLoadTest {
    final String PRODUCT_D1 = "Product 1";
    final String PRODUCT_D2 = "Product 2";
    final String PRODUCT_D3 = "Product 3";

    final String TEST_CUSTOMER = "TEST CUSTOMER";

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    OrderHeader orderHeader;

    Customer customer;

    @BeforeEach
    void setUp() {
        final var customer = Customer.builder().build();
        orderHeader = OrderHeader.builder()
                .customer(customer)
                .build();
        final var orderHeader1 = OrderHeader.builder()
                .customer(customer)
                .build();
        final var orderHeader2 = OrderHeader.builder()
                .customer(customer)
                .build();
        final var orderHeader3 = OrderHeader.builder()
                .customer(customer)
                .build();


        orderHeader.addOrderLine(OrderLine.builder().quantityOrdered(100).build());
        orderHeader.addOrderLine(OrderLine.builder().quantityOrdered(100).build());
        orderHeader1.addOrderLine(OrderLine.builder().quantityOrdered(100).build());
        orderHeader2.addOrderLine(OrderLine.builder().quantityOrdered(100).build());
        orderHeader3.addOrderLine(OrderLine.builder().quantityOrdered(100).build());

        Set<OrderHeader> orderHeaderSet = new HashSet<>();

        orderHeader =  orderHeaderRepository.save(orderHeader);
        orderHeaderSet.add(orderHeader);
        orderHeaderSet.add( orderHeaderRepository.save(orderHeader1));
        orderHeaderSet.add(orderHeaderRepository.save(orderHeader2));
        orderHeaderSet.add(orderHeaderRepository.save(orderHeader3));
        customer.setOrderHeaders(orderHeaderSet);
        this.customer = customerRepository.save(customer);
    }


    @Test
    void testDBLock() {
        final var orderHeader = orderHeaderRepository.findById(21793L);

        final var billTo = new Address();
        billTo.setAddress("Bill me");
        orderHeader.get().setBillToAddress(billTo);
        orderHeaderRepository.saveAndFlush(orderHeader.get());

        System.out.println("I updated the order");
    }

    @Test
    void testN_PlusOneProblem() {

        IntSummaryStatistics totalOrdered = customer.getOrderHeaders().stream()
                .flatMap(orderHeaderItem -> orderHeaderItem.getOrderLines().stream())
                .collect(Collectors.summarizingInt(OrderLine::getQuantityOrdered));

        System.out.println("total ordered: " + totalOrdered.getSum());
    }

    @Test
    void testLazyVsEager() {
        final var orderHeaderById = orderHeaderRepository.findById(orderHeader.getId());

        System.out.println("Order Id is: " + orderHeaderById.get().getId());
        System.out.println("Customer Name is: " + orderHeaderById.get().getCustomer().getId());
    }

//    @Disabled
//    @Rollback(value = false)
    @Test
    void testDataLoader() {
        productRepository.flush();
        List<Product> products = loadProducts();
        Customer customer = loadCustomers();

        int ordersToCreate = 100;

        for (int i = 0; i < ordersToCreate; i++){
            System.out.println("Creating order #: " + i);
            saveOrder(customer, products);
        }

        orderHeaderRepository.flush();
    }

    private OrderHeader saveOrder(Customer customer, List<Product> products){
        Random random = new Random();

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);

        products.forEach(product -> {
            OrderLine orderLine = new OrderLine();
            orderLine.setProduct(product);
            orderLine.setQuantityOrdered(random.nextInt(20));
            orderHeader.addOrderLine(orderLine);
        });

        return orderHeaderRepository.save(orderHeader);
    }

    private Customer loadCustomers() {
        return getOrSaveCustomer(TEST_CUSTOMER);
    }

    private Customer getOrSaveCustomer(String customerName) {
        return customerRepository.findCustomerByCustomerNameIgnoreCase(customerName)
                .orElseGet(() -> {
                    Customer c1 = new Customer();
                    c1.setCustomerName(customerName);
                    c1.setEmail("test@example.com");
                    Address address = new Address();
                    address.setAddress("123 Main");
                    address.setCity("New Orleans");
                    address.setState("LA");
                    c1.setAddress(address);
                    return customerRepository.save(c1);
                });
    }
    private List<Product> loadProducts(){
        List<Product> products = new ArrayList<>();

        products.add(getOrSaveProduct(PRODUCT_D1));
        products.add(getOrSaveProduct(PRODUCT_D2));
        products.add(getOrSaveProduct(PRODUCT_D3));

        return products;
    }
    private Product getOrSaveProduct(String description) {
        return productRepository.findByDescription(description)
                .orElseGet(() -> {
                    Product p1 = new Product();
                    p1.setDescription(description);
                    p1.setProductStatus(ProductStatus.NEW);
                    return productRepository.save(p1);
                });
    }

}