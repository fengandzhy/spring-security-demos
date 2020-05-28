package org.zhouhy.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/admin/page")
    public String admin(){
        //throw new NullPointerException("numm");
        return "admin";
    }

    @GetMapping("/user/page")
    public String user(){
        return "user";
    }
}
