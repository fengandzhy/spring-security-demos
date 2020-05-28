package org.zhouhy.springsecurity.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zhouhy.springsecurity.enums.ResultCode;
import org.zhouhy.springsecurity.reponse.ErrorResult;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(basePackages = "org.zhouhy.springsecurity")
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleForbidden(Throwable e, HttpServletRequest request){
        ResultCode code = ResultCode.getResultCodeByDesc(e.getMessage());
        ErrorResult error =ErrorResult.fail(code, e);
        log.error("URL:{} ,系统异常: ",request.getRequestURI(), e);
        return error;
    }


    //@ExceptionHandler(Throwable.class)
//    public ErrorResult handleNotFound(Throwable e, HttpServletRequest request){
//        ResultCode code = ResultCode.getResultCodeByDesc(e.getMessage());
//        ErrorResult error =ErrorResult.fail(code, e);
//        log.error("URL:{} ,系统异常: ",request.getRequestURI(), e);
//        return error;
//    }

}
