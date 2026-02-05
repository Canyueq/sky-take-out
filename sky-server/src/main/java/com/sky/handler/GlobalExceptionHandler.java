package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.constant.MessageConstant;
import com.sky.exception.SqlIntegrityConstraintViolationException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.bridge.Message;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理sql异常
     * @param ex
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    public Result handleDataAccessException(DataAccessException ex) {
        String msg = ex.getMessage();
        
        if (msg != null && msg.contains("重复键违反唯一约束") && msg.contains("username")) {
            String username = "该账号";
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\(username\\)=\\(([^)]+)\\)");
            java.util.regex.Matcher matcher = pattern.matcher(msg);
    
            if (matcher.find()) {
                username = matcher.group(1); // 提取括号内的值
            }
            return Result.error(username + MessageConstant.ALREADY_EXISTS);
        }

        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
