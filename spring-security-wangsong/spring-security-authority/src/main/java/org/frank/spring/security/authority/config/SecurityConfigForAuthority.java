package org.frank.spring.security.authority.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


import java.io.PrintWriter;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfigForAuthority extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("frank").password("123").roles("admin").build());
        manager.createUser(User.withUsername("sam").password("123").roles("user").build());        
        return manager;
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

//                .antMatchers("/admin/**").hasRole("admin")
//                .antMatchers("/user/**").hasRole("user")
//                .antMatchers("/admin/**").hasAnyRole("admin","user")
//                .antMatchers("/user/**").hasRole("user")
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
                    .csrf().disable().exceptionHandling()
                    .authenticationEntryPoint((req, resp, authException) -> {
                                resp.setContentType("application/json;charset=utf-8");
                                PrintWriter out = resp.getWriter();
                                out.write("you have not login, please login!");
                                out.flush();
                                out.close();
                            }
                );
    }
}
