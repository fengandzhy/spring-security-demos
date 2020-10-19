package org.zhouhy.springsecurity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.zhouhy.springsecurity.security.AuthenticationEntryPointImpl;
import org.zhouhy.springsecurity.security.AuthenticationFailureHandlerImpl;
import org.zhouhy.springsecurity.security.AuthenticationSuccessHandlerImpl;
import org.zhouhy.springsecurity.security.LogoutSuccessHandlerImpl;
import org.zhouhy.springsecurity.service.UserService;

import javax.sql.DataSource;


@Configuration
public class JPASecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }

    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/dologin")
                .usernameParameter("name")
                .passwordParameter("pwd")
                .successHandler(new AuthenticationSuccessHandlerImpl())
                .failureHandler(new AuthenticationFailureHandlerImpl())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/dologout")
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointImpl());
    }
}
