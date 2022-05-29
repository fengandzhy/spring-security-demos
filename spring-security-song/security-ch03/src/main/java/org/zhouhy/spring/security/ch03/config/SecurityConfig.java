package org.zhouhy.spring.security.ch03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login.html")                
                .permitAll()
                .and()
                .csrf().disable();     
    }
}
