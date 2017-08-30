package com.huihuicai.aspectj.annotation;

import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ybm on 2017/8/30.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionAnnotation {
    String permission();
}
