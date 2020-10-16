package org.zhouhy.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zhouhy.springsecurity.security.AuthenticationEntryPointImpl;
import org.zhouhy.springsecurity.security.AuthenticationFailureHandlerImpl;
import org.zhouhy.springsecurity.security.AuthenticationSuccessHandlerImpl;
import org.zhouhy.springsecurity.security.LogoutSuccessHandlerImpl;


@Configuration
public class JsonLoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("zhouhy")
                .password("123456")
                .roles("admin");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
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
