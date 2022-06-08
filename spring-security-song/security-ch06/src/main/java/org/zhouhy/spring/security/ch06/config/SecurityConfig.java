package org.zhouhy.spring.security.ch06.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

/**
 * 这个类必须继承于WebSecurityConfigurerAdapter, 并且有@Configuration 注解 
 * 
 * */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    // 这是个密码加密的bean
    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    // 设置用户名和密码的
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.inMemoryAuthentication()
                .withUser("zhouhy")
                .password("123456")
                .roles("admin");
    }

    /**
     * 这些目录里的资源可以不被拦截，需要注意的是
     * 虽然main.css在static/css里面, 但是这里还是要写成/css/** 不能写成"/static/css/**
     * 因为这个static是静态资源的默认的访问目录
     * */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    }
    
    /**
     * 1. 这里如果没有特别指明, 登录页面是 login.html 然后 登录的action 也会是 login.html只不过一个是get请求， 一个是post 请求
     * 2. anyRequest().authenticated() 表示任何请求都要被认证
     * 3. permitAll() 表示and() 里面的这个请求可以不用认证直接访问
     * 
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                    .formLogin()                    
                    .loginProcessingUrl("/login")
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
                    .permitAll()
                .and()
                    .logout()                
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login.html")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll()
                .and()
                    .csrf().disable();     
    }
}
