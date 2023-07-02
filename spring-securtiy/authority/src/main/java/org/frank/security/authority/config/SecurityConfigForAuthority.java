package org.frank.security.authority.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.frank.security.authority.component.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.PrintWriter;

/**
 * @EnableGlobalMethodSecurity详解
 * @EnableGlobalMethodSecurity(securedEnabled=true) 开启@Secured 注解过滤权限
 * @EnableGlobalMethodSecurity(jsr250Enabled=true)开启@RolesAllowed 注解过滤权限
 * @EnableGlobalMethodSecurity(prePostEnabled=true) 使用表达式时间方法级别的安全性         4个注解可用
 * @PreAuthorize 在方法调用之前, 基于表达式的计算结果来限制对方法的访问
 * @PostAuthorize 允许方法调用, 但是如果表达式计算结果为false, 将抛出一个安全性异常
 * @PostFilter 允许方法调用, 但必须按照表达式来过滤方法的结果
 * @PreFilter 允许方法调用, 但必须在进入方法之前过滤输入值
 * 如果把其中一个设置成false securedEnabled=false， @Secured 将不会起作用， 它并不会报错. 
 * */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfigForAuthority extends WebSecurityConfigurerAdapter {

    private UserSecurityService userSecurityService;
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

       

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()      
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/doLogin")
                    .usernameParameter("name")
                    .passwordParameter("pwd")
                    .successHandler((req, resp, authentication) -> {
                        Object principal = authentication.getPrincipal();
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(principal));
                        out.flush();
                        out.close();
                    })
                    .failureHandler((req, resp, e) -> {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(e.getMessage());
                        out.flush();
                        out.close();
                    })
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler((req, resp, authentication) -> {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write("logout success!");
                        out.flush();
                        out.close();
                    })
                    .deleteCookies()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .permitAll()
                .and()
                    .csrf().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint((req, resp, authException) -> {
                            resp.setContentType("application/json;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            out.write("you have not login, please login!");
                            out.flush();
                            out.close();
                    }).accessDeniedHandler((req,resp,authentication) ->{
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write("you have no permission to access this.");
                        out.flush();
                        out.close();
                    });
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }
}
