package org.frank.spring.security.beginning.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    
    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name){
        return "hello," + name;
    }

//    @GetMapping("/success")
    @RequestMapping("/success")
    public String success(){
        return "login success.";        
    }

    @RequestMapping("/fail")
    public String fail(){
        return "login failed.";
    }
}
