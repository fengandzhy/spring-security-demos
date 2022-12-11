package org.frank.spring.security.spring.data.config;

import org.frank.spring.security.spring.data.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfigForSpringData extends WebSecurityConfigurerAdapter {

    private UserService userService;
    
    public SecurityConfigForSpringData(UserService userService){
        this.userService = userService;
    }
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();        
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
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

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/doLogin")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/welcome")
                    .failureUrl("/fail")
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
                    .csrf().disable();
    }
}
