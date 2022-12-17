package com.nowak.kamil.hibernatejavamapping.creditcard.domain;

import com.nowak.kamil.hibernatejavamapping.common.domain.BaseEntity;
import com.nowak.kamil.hibernatejavamapping.common.interceptor.EncryptedString;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Setter
@Slf4j
@EntityListeners(CreditCardJPACallback.class)
public class CreditCard extends BaseEntity {

    @EncryptedString
    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    @Version
    private Integer version;

    @PrePersist
    public void prePersistCallback() {
        log.info("JPA Callback prePersist");
    }
}
