package com.nowak.kamil.hibernatejavamapping.creditcard.domain;

import com.nowak.kamil.hibernatejavamapping.common.config.SpringContextHelper;
import com.nowak.kamil.hibernatejavamapping.common.service.EncryptionService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CreditCartConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attr) {
        return getEncryptionService().encrypt(attr);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return getEncryptionService().decrypt(dbData);
    }

    private EncryptionService getEncryptionService() {
        return SpringContextHelper.getApplicationContext().getBean(EncryptionService.class);
    }
}
