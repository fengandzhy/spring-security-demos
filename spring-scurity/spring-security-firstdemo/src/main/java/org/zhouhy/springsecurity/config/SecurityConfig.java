package org.zhouhy.springsecurity.config;




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
     * 自定义登录页面, 注意如果这里输入
     * .loginPage("/views/login.html")那么我们就可以访问到views下面的login.html
     * 浏览器里输入 http://localhost:8080/views/login.html
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .permitAll()
                .and()
                .csrf().disable();
    }

    /**
     * 这段配置会覆盖掉配置文件里的配置
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("Sam").password("123").roles("admin");
    }


}
