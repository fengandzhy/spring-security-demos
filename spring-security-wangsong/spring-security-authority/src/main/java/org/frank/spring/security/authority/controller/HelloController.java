package org.frank.spring.security.authority.controller;

import org.frank.spring.security.authority.service.HelloService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {
    
    private HelloService service;
    
    public HelloController(HelloService service){
        this.service = service;
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/admin/hello")
//    @PreAuthorize("hasAuthority('ROLE_admin')")
    @PreAuthorize("hasRole('admin')")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user/hello")
//    @PreAuthorize("hasAuthority('ROLE_user')")
    @PreAuthorize("hasRole('admin')")    
    public String user() {
        return "user";
    }

    /**
     * 1 这里的principle 表示当前用户
     * */
    @GetMapping("/frank")
    @PreAuthorize("principal.username.equals('frank')")
    public String frank() {
        return "frank";
    }

    @GetMapping("/users")
    @Secured("ROLE_admin")
    public String getAllUser() {
        List<String> users = service.getAllUser();
        return users.toString();
    }

}
