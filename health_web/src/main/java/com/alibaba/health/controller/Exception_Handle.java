package com.alibaba.health.controller;

import com.alibaba.health.entity.Result;
import com.alibaba.health.exception.Myexception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author:ZZZ
 * @Date: 2021/1/8 11:17
 * @Version 1.0
 */
//@ControllerAdvice//通知
//@ResponseBody//返回json  大概是异常通知
@RestControllerAdvice
public class Exception_Handle {
//    日志打印
   public static final Logger log = LoggerFactory.getLogger(Exception_Handle.class);

//   异常处理类型

    @ExceptionHandler(Myexception.class)
    public Result handelMyException(Myexception myexception){
        return new Result(false,myexception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handelException(Exception e){
        return new Result(false,"系统异常");
    }

}
