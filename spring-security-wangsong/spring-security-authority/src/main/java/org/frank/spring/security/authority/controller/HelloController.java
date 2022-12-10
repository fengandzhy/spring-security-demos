package org.frank.spring.security.authority.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @PreAuthorize("principal.username.equals('frank')")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/admin/hello")
    @PreAuthorize("hasAuthority('admin')")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user/hello")
    @PreAuthorize("hasAuthority('user')")
    public String user() {
        return "user";
    }
}
