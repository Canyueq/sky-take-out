package com.sky.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

import com.sky.enumeration.OperationType;

/*
*自定义注解
 */
//注解对象
@Target(ElementType.METHOD)
//注解添加时机
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //数据库操作insert,update
    OperationType value();
}
