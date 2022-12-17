package com.nowak.kamil.hibernatejavamapping.creditcard.domain;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreditCardJPACallback {

    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate(final CreditCard creditCard) {
        log.info("CreditCardJPACallback beforeInsertOrUpdate");
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    public void postLoad(final CreditCard creditCard) {
        log.info("CreditCardJPACallback postLoad");
    }
}
