package com.wynne.apt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ybm on 2017/9/4.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface BindView {
    int value();
}
