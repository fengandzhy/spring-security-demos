package org.zhouhy.springsecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Secured({"ROLE_PRODUCT","ROLE_ADMIN"})
    @RequestMapping(value = "/findAll",method =RequestMethod.GET)
    public String findAll(){
        return "product-list";
    }
}
