package com.eqshen.demo.whitelist.annotation;

import java.lang.annotation.*;

/**
 * @author eqshen
 * @description
 * @date 2021/4/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface ApplyWhiteList {
    String key() default "";

    String returnResult() default "";
}
