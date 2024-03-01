package org.frank.spring.security.remember.me.config;

import org.frank.spring.security.remember.me.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfigForRememberMe extends WebSecurityConfigurerAdapter {

    private UserService userService;
    
    private DataSource dataSource; // 数据源

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
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    }

    /**
     * 持久化Token存储
     * */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource); // 设置数据源
        return tokenRepository;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/hello").rememberMe() // 必须通过自动认证后才能访问
                .antMatchers("/userInfo").fullyAuthenticated() // 必须用户名密码才能访问
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
                    .rememberMe()
                    .key("zhou_hy") // 设置用于生成记住我token的密钥
                    .userDetailsService(userService) // 设置userDetailsService
                    .tokenRepository(persistentTokenRepository()) // 设置数据访问层
                    .tokenValiditySeconds(60 * 60) // 记住我的时间(秒)
                .and()
                    .csrf().disable();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
