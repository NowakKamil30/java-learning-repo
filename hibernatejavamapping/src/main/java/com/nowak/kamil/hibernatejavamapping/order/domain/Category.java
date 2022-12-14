package com.nowak.kamil.hibernatejavamapping.order.domain;

import com.nowak.kamil.hibernatejavamapping.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class Category extends BaseEntity {

    private String description;

    @ManyToMany
    @JoinTable(name = "product_category",
    joinColumns = @JoinColumn(name = "category_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(description, category.description) && Objects.equals(products, category.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }
}
