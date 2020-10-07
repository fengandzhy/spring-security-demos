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

import java.io.PrintWriter;

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
                    .successHandler(((request, response, authentication) -> {
                        Object principal = authentication.getPrincipal();
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(principal));
                        out.flush();
                        out.close();
                    }))
                    .failureHandler(((request, response, exception) -> {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write(exception.getLocalizedMessage());
                        out.flush();
                        out.close();
                    }))
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/dologout")
                    .logoutSuccessHandler(((request, response, authentication) -> {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write("注销成功");
                        out.flush();
                        out.close();
                    }))
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write("尚未登录，请先登录");
                    out.flush();
                    out.close();
                }));
    }
}
