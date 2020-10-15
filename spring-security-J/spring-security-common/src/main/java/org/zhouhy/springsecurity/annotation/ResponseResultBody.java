package org.zhouhy.springsecurity.annotation;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
@ResponseBody
public @interface ResponseResultBody {
}
