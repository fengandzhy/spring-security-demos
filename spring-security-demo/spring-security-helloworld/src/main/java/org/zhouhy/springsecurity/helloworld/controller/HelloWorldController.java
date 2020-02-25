package org.zhouhy.springsecurity.helloworld.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * ��goals������ clean install -X����mvn clean install��
 * */
@Controller
public class HelloWorldController {
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
        model.addAttribute("greeting", "Hi, Welcome to mysite. ");
        return "welcome";
    }
 
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "admin";
    }
 
    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public String dbaPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "dba";
    }
 
    /**
     * �˳�������û��/login����Ϊspring security Ĭ�ϲ���
     * */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
       public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          if (auth != null){    
             new SecurityContextLogoutHandler().logout(request, response, auth);
          }
          return "welcome";
       }
 
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }
     
    /**
     * ���ش�Spring SecurityContext�м�¼�ĵ�¼���û�
     * */
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

}
