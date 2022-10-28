package org.frank.spring.security.beginning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name){
        return "hello," + name;
    }

    @GetMapping("/success")
    public String success(){
        return "login success.";        
    }
}
