package com.adtech.rts.model.annotation;


import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ConvertProperty {

    //字段名
    String columnName() default "";
}
