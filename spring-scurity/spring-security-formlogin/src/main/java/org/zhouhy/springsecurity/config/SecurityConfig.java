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

import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    /**
     * 过滤一些静态元素
     * */
    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }

    /**
     * successHandler 方法的参数是一个 AuthenticationSuccessHandler 对象，
     * 这个对象中我们要实现的方法是 onAuthenticationSuccess
     *
     *
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("name")
                .passwordParameter("pwd")
                .successHandler(new AuthenticationSuccessHandlerImpl())
                .failureHandler(new AuthenticationFailureHandlerImpl())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")//这个是get请求的注销登录
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointImpl());
    }

    /**
     * 这段配置会覆盖掉配置文件里的配置
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("Sam").password("123").roles("admin");
    }


}
