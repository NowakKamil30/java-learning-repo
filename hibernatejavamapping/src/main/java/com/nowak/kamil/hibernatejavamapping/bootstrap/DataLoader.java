package com.nowak.kamil.hibernatejavamapping.bootstrap;

import com.nowak.kamil.hibernatejavamapping.order.domain.Address;
import com.nowak.kamil.hibernatejavamapping.order.domain.OrderHeader;
import com.nowak.kamil.hibernatejavamapping.order.repository.OrderHeaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final OrderHeaderRepository orderHeaderRepository;

    @Override
    public void run(String... args) throws Exception {
        orderHeaderRepository.save(OrderHeader.builder()
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
                .build());
    }
}
