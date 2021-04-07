# formlogin

---

This demo is about a customized login page, the techniques used in this example are `SpringBoot` `SpringSecurity` `Thymeleaf` `JPA` `Bootstrap` `CSS` `HTML5` 

two steps to use this demo
 - create a database the name is written in `application.properties`
 - run the `CreateUsersTest`
 

## The summary of knowledge points in this demo  
### 1. In this demo, it shows three ways to get information about the logged-in user.
```java
    @RequestMapping(value="/welcome",method={RequestMethod.GET,RequestMethod.POST})
    public String welcome(Authentication authentication,Model model){
        String username = authentication.getName();
        model.addAttribute("username",username);
        return "/views/welcome";
    }
```

```java
        @RequestMapping(value="/welcome",method={RequestMethod.GET,RequestMethod.POST})
    public String welcome(HttpServletRequest request, Model model){
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        model.addAttribute("username",username);
        return "/views/welcome";
    }
```
```java
        @RequestMapping(value="/welcome",method={RequestMethod.GET,RequestMethod.POST})
    public String welcome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username",username);
        return "/views/welcome";
    }
```
### 2. If you want to use SpringSecurity tags in your Thymeleaf pages, you have to follow these steps 
   

 - add below dependency to your pom file 
```xml
       <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity5</artifactId>
        </dependency>
```
  
 - add ```xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5" ``` in your Thymeleaf pages where you want you to use SpringSecurity tags. If your version of SpringSecurtiy is 4.x you need to use ```xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4```
 


 

