package org.frank.spring.security.authority.controller;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {
    
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

    @PostFilter("filterObject.lastIndexOf('2')!=-1")
    public List<String> getAllUser() {
        List<String> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add("javaboy:" + i);
        }
        return users;
    }

}
