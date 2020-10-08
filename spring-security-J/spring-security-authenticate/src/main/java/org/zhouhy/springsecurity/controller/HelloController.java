package org.zhouhy.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value="/user")
    public String user(){
        return " This is user";
    }


    @RequestMapping(value="/admin")
    public String admin(){
        return " This is admin";
    }

    @RequestMapping(value="/hello")
    public String hello(){
        return " This is hello";
    }
}
