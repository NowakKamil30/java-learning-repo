package com.nowak.kamil.hibernatejavamapping.bootstrap;

import com.nowak.kamil.hibernatejavamapping.domain.Category;
import com.nowak.kamil.hibernatejavamapping.domain.OrderLine;
import com.nowak.kamil.hibernatejavamapping.domain.Product;
import com.nowak.kamil.hibernatejavamapping.repository.OrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final OrderHeaderRepository orderHeaderRepository;

    // General working solution(if we want to assign readOrderData to transaction instead of run.
    // We should call readOrderData using proxy(easier way of doing it is replaced readOrderData to anew component)

    @Override
    @Transactional(readOnly = true)
    public void run(String... args) throws Exception {
        log.info("Run Bootstrap");
        readOrderData();
    }

    private void readOrderData() {
        orderHeaderRepository.findById(1L).ifPresentOrElse(orderHeader -> {
            orderHeader.getOrderLines().stream()
                    .map(OrderLine::getProduct)
                    .map(Product::getDescription)
                    .forEach(log::info);

            orderHeader.getOrderLines().stream()
                    .map(OrderLine::getProduct)
                    .map(Product::getCategories)
                    .flatMap(Collection::stream)
                    .map(Category::getDescription)
                    .forEach(log::info);
        }, () -> log.error("orderHeader not found"));
    }
}