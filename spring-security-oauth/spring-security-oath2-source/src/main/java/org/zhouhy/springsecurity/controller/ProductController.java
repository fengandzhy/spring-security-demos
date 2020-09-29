package org.zhouhy.springsecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    //@Secured({"ROLE_PRODUCT","ROLE_ADMIN"})
    @RequestMapping(value = "/findAll",method =RequestMethod.GET)
    public String findAll(){
        System.out.println("order");
        return "product-list";
    }
}
