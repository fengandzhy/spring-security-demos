package org.frank.spring.security.json.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.PrintWriter;

@Configuration
public class SecurityConfigForJson extends WebSecurityConfigurerAdapter {
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("frank")
                .password("654321").roles("admin");
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
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
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
                                ObjectMapper mapper = new ObjectMapper();
                                ObjectNode login = mapper.createObjectNode();
                                ObjectNode loginStatus = mapper.createObjectNode();
                                loginStatus.put("status",1);
                                loginStatus.put("message","you have not login, please login!");
                                login.set("result",loginStatus);
                                out.write(mapper.writeValueAsString(login));
                                out.flush();
                                out.close();
                            }
                    );
    }
}
