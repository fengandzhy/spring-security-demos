package org.zhouhy.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zhouhy.springsecurity.annotation.ResponseResultBody;

@RestController
@ResponseResultBody
public class HelloController {

    @RequestMapping(value="/hello",method = RequestMethod.GET)
    public String sayHello(){
        return "hello world";
    }

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(){
        return "hello index";
    }

    @RequestMapping(value="/fail")
    public String fail(){
        return "login fail";
    }

    @RequestMapping(value="/logout.html")
    public String logout(){
        return "logout ";
    }
}
