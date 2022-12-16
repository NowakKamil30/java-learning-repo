package com.nowak.kamil.hibernatejavamapping.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CreditCard extends BaseEntity {

    private String creditCardNumber;
    private String cvv;
    private String expirationDate;
    @Version
    private Integer version;
}
