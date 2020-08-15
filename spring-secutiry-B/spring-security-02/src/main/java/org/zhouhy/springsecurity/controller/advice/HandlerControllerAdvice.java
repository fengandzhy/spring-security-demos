package org.zhouhy.springsecurity.controller.advice;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(){
        return "redirect:/403.jsp";
    }

    @ExceptionHandler(Throwable.class)
    public String handleOtherException(){
        return "redirect:/500.jsp";
    }
}
