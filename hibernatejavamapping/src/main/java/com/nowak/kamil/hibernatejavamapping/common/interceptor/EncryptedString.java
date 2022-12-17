package com.nowak.kamil.hibernatejavamapping.common.interceptor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EncryptedString {
}
