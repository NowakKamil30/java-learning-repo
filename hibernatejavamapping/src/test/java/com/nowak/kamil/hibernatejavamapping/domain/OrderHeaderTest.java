package com.nowak.kamil.hibernatejavamapping.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderHeaderTest {

    final Long id = 1L;
    final Customer consumer = new Customer();

    @Test
    void returnTrueWhenOnlyIdSetAndEquals() {
        final var o1 = OrderHeader.builder()
                .id(id)
                .build();
        final var o2 = OrderHeader.builder()
                .id(id)
                .build();

        assertTrue(o1.equals(o2));
    }

    @Test
    void returnTrueWhenIdAndConsumerSetAndEquals() {
        final var o1 = OrderHeader.builder()
                .id(id)
                .customer(consumer)
                .build();
        final var o2 = OrderHeader.builder()
                .id(id)
                .customer(consumer)
                .build();

        assertTrue(o1.equals(o2));
    }

    @Test
    void returnFalseWhenIdNotEquals() {
        final var o1 = OrderHeader.builder()
                .id(id + 1)
                .customer(consumer)
                .build();
        final var o2 = OrderHeader.builder()
                .id(id)
                .customer(consumer)
                .build();

        assertFalse(o1.equals(o2));
    }

    @Test
    void returnFalseWhenConsumerNotEquals() {
        final var o1 = OrderHeader.builder()
                .id(id)
                .customer(consumer)
                .build();
        final var o2 = OrderHeader.builder()
                .id(id)
                .customer(Customer.builder().customerName("21321").build())
                .build();

        assertFalse(o1.equals(o2));
    }

    @Test
    void returnFalseWhenIdAndConsumerNotEquals() {
        final var o1 = OrderHeader.builder()
                .id(id + 1)
                .customer(consumer)
                .build();
        final var o2 = OrderHeader.builder()
                .id(id)
                .customer(new Customer())
                .build();

        assertFalse(o1.equals(o2));
    }

    @Test
    void returnTrueWhenObject1AndObject3equalsToObject2() {
        final var o1 = OrderHeader.builder()
                .id(id)
                .customer(consumer)
                .build();
        final var o2 = OrderHeader.builder()
                .id(id)
                .customer(consumer)
                .build();
        final var o3 = OrderHeader.builder()
                .id(id)
                .customer(consumer)
                .build();

        assertTrue(o1.equals(o2));
        assertTrue(o3.equals(o2));
        assertTrue(o1.equals(o3));
    }

    @Test
    void returnTrueWhenTheSameObject() {
        final var o1 = OrderHeader.builder()
                .id(id)
                .customer(consumer)
                .build();

        assertTrue(o1.equals(o1));
    }


}