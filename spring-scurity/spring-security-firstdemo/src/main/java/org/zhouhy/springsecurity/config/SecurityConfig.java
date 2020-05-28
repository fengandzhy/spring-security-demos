package org.zhouhy.springsecurity.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
     * .loginPage("/login.html") 这是登录页面, 如果不配.loginProcessingUrl 登录接口(form表单里的action值)也默认是/login.html
     * .loginProcessingUrl("/doLogin") 这个就是登录接口
     * 另外表单里默认的参数是username和password <input type="text" name="username" id="name"> <input type="password" name="password" id="pass">
     * 但是配了.usernameParameter("name") 和 .passwordParameter("pwd") 那么表单的参数也会改变
     * .permitAll() 表示第一个and之后到第二个and之前的东西不要拦截,也就是说login.html 这个请求不要拦截
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
                //.successForwardUrl("/success") //服务器端跳转
                .defaultSuccessUrl("/success") //页面端重定向, 默认到/success, 如果之前是其他页面跳转到login.html的就返回其他页面
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")//这个是get请求的注销登录
                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST")) //post请求的注销登录
                .logoutSuccessUrl("/login.html") //登录退出以后的页面
                .invalidateHttpSession(true) // 默认是true, 注销session
                .clearAuthentication(true) // 默认是true清除认证信息
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
