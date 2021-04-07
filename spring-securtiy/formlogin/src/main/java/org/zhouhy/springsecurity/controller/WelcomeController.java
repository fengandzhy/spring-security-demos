package org.zhouhy.springsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class WelcomeController {
    
//    @RequestMapping(value="/welcome",method={RequestMethod.GET,RequestMethod.POST})
//    public String welcome(Authentication authentication,Model model){
//        String username = authentication.getName();
//        model.addAttribute("username",username);
//        return "/views/welcome";
//    }

//    @RequestMapping(value="/welcome",method={RequestMethod.GET,RequestMethod.POST})
//    public String welcome(HttpServletRequest request, Model model){
//        Principal principal = request.getUserPrincipal();
//        String username = principal.getName();
//        model.addAttribute("username",username);
//        return "/views/welcome";
//    }

    @RequestMapping(value="/welcome",method={RequestMethod.GET,RequestMethod.POST})
    public String welcome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username",username);
        return "/views/welcome";
    }
    
    

//    @RequestMapping(value="/userInfo",method={RequestMethod.GET,RequestMethod.POST})
//    public String userInfo(HttpServletRequest request, Model model){
//        Principal principal = request.getUserPrincipal();
//        String username = principal.getName();
//        model.addAttribute("username",username);
//        return "/views/userinfo";
//    }

    @RequestMapping(value="/hello",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String hello(){
        return "hello";
    }

    @RequestMapping(value="/fail",method={RequestMethod.GET,RequestMethod.POST})    
    public String login(Model model){        
        model.addAttribute("fail","fail login222");
        return "/login";
    }    
    
    @RequestMapping(value="/login.html",method={RequestMethod.GET,RequestMethod.POST})
    public String loginPage(){        
        return "/login";
    }
    
    
}
