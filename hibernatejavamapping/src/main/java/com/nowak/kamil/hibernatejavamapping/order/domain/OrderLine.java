package com.nowak.kamil.hibernatejavamapping.order.domain;

import com.nowak.kamil.hibernatejavamapping.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class OrderLine extends BaseEntity {

    private Integer quantityOrdered;

    @ManyToOne
    private OrderHeader orderHeader;

    @ManyToOne
    private Product product;

    @Version
    private Integer version;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final OrderLine orderLine = (OrderLine) o;
        return Objects.equals(quantityOrdered, orderLine.quantityOrdered) && Objects.equals(orderHeader, orderLine.orderHeader) && Objects.equals(product, orderLine.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quantityOrdered, orderHeader, product);
    }
}
