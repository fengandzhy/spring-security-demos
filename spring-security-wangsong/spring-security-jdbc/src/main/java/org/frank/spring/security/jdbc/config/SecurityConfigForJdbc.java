package org.frank.spring.security.jdbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfigForJdbc extends WebSecurityConfigurerAdapter {

    
    private DataSource dataSource;

    public SecurityConfigForJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }    

    @Override
    protected UserDetailsService userDetailsService() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        if(!jdbcUserDetailsManager.userExists("Frank")){
            jdbcUserDetailsManager.createUser(User.withUsername("Frank").password("123456").roles("admin","user").build());
        }
        if(!jdbcUserDetailsManager.userExists("Frank")){
            jdbcUserDetailsManager.createUser(User.withUsername("Sam").password("654321").roles("user").build());
        }
        return jdbcUserDetailsManager;
    }
}
