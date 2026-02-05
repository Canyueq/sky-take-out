package com.sky.exception;

/*
SQL异常
*/
public class SqlIntegrityConstraintViolationException extends RuntimeException {
    
    public SqlIntegrityConstraintViolationException(){

    }

    public SqlIntegrityConstraintViolationException(String msg){
        super(msg);
    }
}
