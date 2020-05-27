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
                .successHandler((req,resp,authentication)->{
                    Object principle = authentication.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principle));
                    out.flush();
                    out.close();
                })
                .failureHandler((req,resp,exception)->{
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(exception.getMessage()));
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")//这个是get请求的注销登录
                .logoutSuccessHandler((req,resp,authentication)->{
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString("注销成功!"));
                    out.flush();
                    out.close();
                })
                .and()
                .csrf().disable().exceptionHandling().authenticationEntryPoint((req,resp,exception)->{
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString("尚未登录，请登录"));
                    out.flush();
                    out.close();
                });
    }

    /**
     * 这段配置会覆盖掉配置文件里的配置
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("Sam").password("123").roles("admin");
    }


}
