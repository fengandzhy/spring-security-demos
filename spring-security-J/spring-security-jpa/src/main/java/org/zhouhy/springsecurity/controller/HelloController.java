package org.zhouhy.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhouhy.springsecurity.annotation.ResponseResultBody;

@ResponseResultBody
@RestController
public class HelloController {

    @RequestMapping(value="/user/b")
    public String user(){
        return " This is user";
    }


    @RequestMapping(value="/admin/a")
    public String admin(){
        return " This is admin";
    }

    @RequestMapping(value="/hello")
    public String hello(){
        return " This is hello";
    }
}
