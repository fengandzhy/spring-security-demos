package org.frank.spring.security.authority.controller;

import org.frank.spring.security.authority.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
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
    @PreAuthorize("hasRole('user')")    
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

    @GetMapping("/ages")
    @Secured("ROLE_user")
//    @PreAuthorize("hasRole('user')")
//    @PreAuthorize("hasAuthority('ROLE_user')")
    public void getAllAges() {
        List<Integer> ages = new ArrayList<>();
        ages.add(1);
        ages.add(2);
        ages.add(3);
        ages.add(4);
        
        List<String> users = new ArrayList<>();
        users.add("A");
        users.add("B");
        users.add("C");
        
        service.getAllAge(ages,users);        
    }

    @GetMapping("/test_post")
    @PostAuthorize("hasAuthority('ROLE_admin')")
    public String postAuthorize(){
        logger.info("In the method. ");
        return "postAuthorize";
    }

}
