package org.zhouhy.springsecurity.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.WebUtils;
import org.zhouhy.springsecurity.annotation.ResponseResultBody;
import org.zhouhy.springsecurity.exception.ResultException;
import org.zhouhy.springsecurity.result.Result;
import org.zhouhy.springsecurity.utils.JsonUtil;

import java.lang.annotation.Annotation;

@Slf4j
@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Class<? extends Annotation> ANNOTAIONT_TYPE = ResponseResultBody.class;


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(),ANNOTAIONT_TYPE)|| returnType.hasMethodAnnotation(ANNOTAIONT_TYPE);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof Result){
            return body;
        }
        if (body instanceof String) {
            return JsonUtil.object2Json(Result.success(body));
        }
        return Result.success(body);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Result<?>> exceptionHandler(Exception ex, WebRequest request){
        log.error("ExceptionHandler:{}",ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        if(ex instanceof ResultException){
            return this.handleResultException((ResultException)ex,headers,request);
        }
        return handleException(ex,headers,request);
    }

    protected ResponseEntity<Result<?>> handleResultException(ResultException ex,HttpHeaders headers,WebRequest request){
        Result<?> body = Result.fail(ex.getResultStatus());
        HttpStatus status = ex.getResultStatus().getHttpStatus();
        return this.handleExceptionInternal(ex,body,headers,status,request);
    }

    protected ResponseEntity<Result<?>> handleException(Exception ex,HttpHeaders headers,WebRequest request){
        Result<?> body = Result.fail();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this.handleExceptionInternal(ex,body,headers,status,request);
    }

    protected ResponseEntity<Result<?>> handleExceptionInternal(
            Exception ex,Result<?> body,HttpHeaders headers,HttpStatus status,WebRequest request){
        if(HttpStatus.INTERNAL_SERVER_ERROR.equals(status)){
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE,ex,WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body,headers,status);


    }
}
