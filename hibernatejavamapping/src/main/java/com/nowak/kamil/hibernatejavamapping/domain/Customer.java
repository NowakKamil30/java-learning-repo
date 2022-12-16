package com.nowak.kamil.hibernatejavamapping.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public class Customer extends BaseEntity {
    @Column(length = 50)
    @Length(max = 50)
    private String customerName;

    @Valid
    @Embedded
    private Address address;

    @Column(length = 20)
    @Length(max = 20)
    private String phone;

    @Email
    private String email;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private Set<OrderHeader> orderHeaders;

    @Version
    private Integer version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerName, customer.customerName) && Objects.equals(address, customer.address) && Objects.equals(phone, customer.phone) && Objects.equals(email, customer.email) && Objects.equals(orderHeaders, customer.orderHeaders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customerName, address, phone, email);
    }
}
