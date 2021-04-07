package org.zhouhy.springsecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Secured({"ROLE_ORDER","ROLE_ADMIN"})
    @RequestMapping(value = "/findAll",method =RequestMethod.GET)
    public String findAll(){
        System.out.println("order");
        return "order-list";
    }
}
