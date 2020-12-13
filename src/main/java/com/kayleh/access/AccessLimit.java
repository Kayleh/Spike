package com.kayleh.access;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: Kayleh
 * @Date: 2020/12/13 1:10
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit
{
    int seconds();

    int maxCount();

    boolean needLogin();
}
