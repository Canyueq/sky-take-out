package com.sky.aspect;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Select;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
/*
* 自定义切面
* 注解+通知
*/
public class AutoFillAspect {
    
    /*
    * 切入点    
    */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut(){};

    /**
     * 前置通知，在通知进行公共字段填充
     */
    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行公共字段填充");
        //获取数据库操作类型，通过签名获取到注解，通过注解的value获取操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();        //获取切入点的方法签名对象
        log.info("获取到的签名对象,{},方法,{}",signature,signature.getMethod());
        Method targetMethod = null;
        try {
            targetMethod = signature.getMethod().getDeclaringClass()
                .getMethod(signature.getMethod().getName(), signature.getMethod().getParameterTypes());
        } catch (Exception e) {
            log.info("异常,{}",e);
            // TODO: handle exception
        }
        AutoFill autoFill = targetMethod.getAnnotation(AutoFill.class);        //获取方法的注解对象
        log.info("获取的注解对象,{}",autoFill);
        OperationType operationType = autoFill.value(); //从注解获取参数

        //获取被拦截方法的参数实体
        Object args[] = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return ;
        }

        //约定所有mapper方法第一个参数为实体对象
        Object entity =  args[0];

        //为参数自动填充公共字段
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        try {
        //判断操作类型
            if(operationType == OperationType.INSERT){
                //为4个字段进行填充
                    Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                    Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
                    Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                    Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                    setCreateTime.invoke(entity,now);
                    setCreateUser.invoke(entity,currentId);
                    setUpdateTime.invoke(entity,now); 
                    setUpdateUser.invoke(entity,currentId);
            } else if(operationType == OperationType.UPDATE){
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                setUpdateTime.invoke(entity,now); 
                setUpdateUser.invoke(entity,currentId);
            }

        }catch (Exception e) {
                // TODO: handle exception
            }
     }
}
