package org.frank.spring.security.beginning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 这是个密码加密的方法, 它会创建一个实现了PasswordEncoder接口的类的实例, 并把它注入到容器中
     * 让这个实例去给用户的密码加密
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 这是一个用户产生方式的类
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("frank")
                .password("654321").roles("admin");
    }

    /**
     * 虽然main.css在static/css里面, 但是这里还是要写成/css/** 不能写成"/static/css/**
     * 因为这个static是静态资源的默认的访问目录
     * */
    @Override
    public void configure(WebSecurity web) throws Exception {        
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    }    

    /**
     *  1.在Security中.loginPage("/login.html") 这里如果写成这样,并且在controller里面没有配置过/login.html这个路径,
     *  那么它会以访问静态资源的方式来找到login.html, 那么也就是说在login.html里面一些关于thymeleaf的标签就不起作用了.  
     *  
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/doLogin")
                    .usernameParameter("name")
                    .passwordParameter("pwd")
                    .defaultSuccessUrl("/success",true)
                    .permitAll()
                .and()
                    .csrf().disable();
    }
}
