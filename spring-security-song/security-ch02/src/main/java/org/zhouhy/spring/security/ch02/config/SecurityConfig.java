package org.zhouhy.spring.security.ch02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class SecurityConfig {
    
    // 这是个密码加密的bean
    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    // 设置用户名和密码的
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//                auth.inMemoryAuthentication()
//                .withUser("zhouhy")
//                .password("123456")
//                .roles("admin");
//    }
    
    /**
     * spring boot 2.7.1后 WebSecurityConfigurerAdapter 过期, 以上改成以下.
     * 
     * */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("zhouhy")
                .password("123456")
                .roles("admin")
                .build();
        return new InMemoryUserDetailsManager(user);
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("frank").password("123").roles("admin").build());
//        manager.createUser(User.withUsername("sam").password("123").roles("user").build());
//        return manager;
    }
}
