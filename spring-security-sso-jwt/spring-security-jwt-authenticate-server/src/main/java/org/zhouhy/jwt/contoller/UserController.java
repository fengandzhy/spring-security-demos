package org.zhouhy.jwt.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String update(){
        return "update";
    }
}
