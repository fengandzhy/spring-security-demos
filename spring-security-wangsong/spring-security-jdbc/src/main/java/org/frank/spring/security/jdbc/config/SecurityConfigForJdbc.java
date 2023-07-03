package org.frank.spring.security.jdbc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.frank.spring.security.jdbc.components.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.PrintWriter;

@Configuration
public class SecurityConfigForJdbc extends WebSecurityConfigurerAdapter {

    
//    private DataSource dataSource;
//
//    public SecurityConfigForJdbc(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
    
//    @Bean
//    RoleHierarchy roleHierarchy(){
//        RoleHierarchyImpl impl = new RoleHierarchyImpl();
//        impl.setHierarchy("ROLE_admin > ROLE_user");
//        return impl;
//    }

//    @Bean
//    @Override    
//    protected UserDetailsService userDetailsService() {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
//        jdbcUserDetailsManager.setDataSource(dataSource);
//        if(!jdbcUserDetailsManager.userExists("Frank")){
//            jdbcUserDetailsManager.createUser(User.withUsername("Frank").password("123456").roles("admin").build());
//        }
//        if(!jdbcUserDetailsManager.userExists("Sam")){
//            jdbcUserDetailsManager.createUser(User.withUsername("Sam").password("654321").roles("user").build());
//        }
//        return jdbcUserDetailsManager;
//    }

    private UserSecurityService userSecurityService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/doLogin")
                .usernameParameter("name")
                .passwordParameter("pwd")
                .successHandler((req, resp, authentication) -> {
                    Object principal = authentication.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(e.getMessage());
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("logout success!");
                    out.flush();
                    out.close();
                })
                .deleteCookies()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, authException) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("you have not login, please login!");
                    out.flush();
                    out.close();
                }).accessDeniedHandler((req,resp,authentication) ->{
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("you have no permission to access this.");
                    out.flush();
                    out.close();
                });
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }
}
