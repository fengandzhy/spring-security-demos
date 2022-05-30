package org.zhouhy.spring.security.ch06.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Security";
    }

    @RequestMapping("/success")
    public String success(){
        return "login success.";
    }

    @RequestMapping("/failure")
    public String failure(){
        return "login failure.";
    }
}
