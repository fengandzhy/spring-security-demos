package org.zhouhy.springsecurity.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.zhouhy.springsecurity.reponse.ErrorResult;
import org.zhouhy.springsecurity.reponse.Result;
import org.zhouhy.springsecurity.utils.JsonUtil;

/**
 * 这个地方能够拦截响应返回的数据,但是只能拦截到@RequestMapping("/hello") 这种地方返回的数据
 * 无法拦截到out.write(data); 这里
 * 只要加了这个，它只拦截里面抛上来的(basePackages = "org.zhouhy.springsecurity")
 * */
@Slf4j
@ControllerAdvice(basePackages = "org.zhouhy.springsecurity")
public class ReponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("begin boforeBodyWrite!");
        if(body instanceof ErrorResult){
            ErrorResult errorResult = (ErrorResult)body;
            return Result.fail(errorResult.getStatus(),errorResult.getMessage());
        }

        if (body instanceof String) {
            return JsonUtil.object2Json(Result.success(body));
        }
        return Result.success(body);
    }
}
