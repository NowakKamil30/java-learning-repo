package com.nowak.kamil.hibernatejavamapping.creditcard.domain;

import com.nowak.kamil.hibernatejavamapping.common.domain.BaseEntity;
import com.nowak.kamil.hibernatejavamapping.common.interceptor.EncryptedString;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CreditCard extends BaseEntity {

    @EncryptedString
    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    @Version
    private Integer version;
}
