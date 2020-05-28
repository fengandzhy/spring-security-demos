package org.zhouhy.springsecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.zhouhy.springsecurity.security.AuthenticationEntryPointImpl;
import org.zhouhy.springsecurity.security.AuthenticationFailureHandlerImpl;
import org.zhouhy.springsecurity.security.AuthenticationSuccessHandlerImpl;
import org.zhouhy.springsecurity.security.LogoutSuccessHandlerImpl;



@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }



    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }

    @Bean
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("sam").password("123").roles("admin").build());
        manager.createUser(User.withUsername("frank").password("123").roles("user").build());
        return manager;
    }

    /**
     * 这里是角色继承
     * */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }


    /**
     * .anyRequest().authenticated() 这个一定要放在两个antMatchers之后, 表示除了前面拦截规则之外，剩下的请求要如何处理。
     * .antMatchers("/admmin/**").hasRole("admin") 表示在admin这个路径下的所有请求都要admin权限才能访问
     * 如果不设置.exceptionHandling().authenticationEntryPoint 他会默认返回一个登录页面
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
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
}
