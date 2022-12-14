package com.nowak.kamil.hibernatejavamapping.common.interceptor;

import com.nowak.kamil.hibernatejavamapping.common.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptionInterceptor extends EmptyInterceptor {

    private final EncryptionService encryptionService;
    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        log.info("onLoad");

        final var newState = processFields(entity, state, propertyNames, "onLoad");

        return super.onLoad(entity, id, newState, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        log.info("onFlushDirty");

        final var newState = processFields(entity, currentState, propertyNames, "onFlushDirty");

        return super.onFlushDirty(entity, id, newState, previousState, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        log.info("onSave");

        final var newState = processFields(entity, state, propertyNames, "onSave");

        return super.onSave(entity, id, newState, propertyNames, types);
    }

    private Object[] processFields(Object entity, Object[] state, String[] propertyNames, String type) {
        List<String> annotationFields = getAnnotationFields(entity);

        for (String field : annotationFields) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (propertyNames[i].equals(field)) {
                    if (StringUtils.hasText(state[i].toString())) {
                        if ("onSave".equals(type) || "onFlushDirty".equals(type)) {
                            state[i] = encryptionService.encrypt(state[i].toString());
                        } else if ("onLoad".equals(type)) {
                            state[i] = encryptionService.decrypt(state[i].toString());
                        }
                    }
                }
            }
        }

        return state;
    }

    private List<String> getAnnotationFields(Object entity) {
         final var annotationFields = new ArrayList<String>();

         for (Field field : entity.getClass().getDeclaredFields()) {
             if (!Objects.isNull(field.getAnnotation(EncryptedString.class))) {
                 annotationFields.add(field.getName());
             }
         }

         return annotationFields;
    }
}
