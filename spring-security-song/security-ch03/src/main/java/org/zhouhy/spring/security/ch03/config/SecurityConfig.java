package org.zhouhy.spring.security.ch03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}
