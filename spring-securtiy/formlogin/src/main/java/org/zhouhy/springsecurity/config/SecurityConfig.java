package org.zhouhy.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zhouhy.springsecurity.service.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    UserService userService;
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("zhouhy")
//                .password("123456")
//                .roles("admin");
        auth.userDetailsService(userService);
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
     * .loginPage("/login.html") 这里如果写成这样, 它会根据找静态资源的方式去找login.html, 它不会考虑thymeleaf 的设置
     * classpath:/META-INF/resources/
     * classpath:/resources/
     * classpath:/static/
     * classpath:/public/
     * 
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/doLogin")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    //.defaultSuccessUrl("/welcome")
                    .successForwardUrl("/index")
                    .failureForwardUrl("/fail")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
                    .deleteCookies()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login.html")
                    .permitAll()
                .and()
                    //.csrf().disable()
                ;
//        http.authorizeRequests().anyRequest().permitAll().and().logout().permitAll();             
    }
}
