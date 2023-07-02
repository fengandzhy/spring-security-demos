package org.frank.security.authority.component;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserSecurityService implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * 调用形式有两种：
         *   1. 此时构建的 SimpleGrantedAuthority 必须是以 ROLE_ 开头, 例如 ROLE_admin, ROLE_manager.
         *      实现全权限控制的时候使用 @RolesAllowed("ROLE_admin")  或者 @RolesAllowed("admin") 都可以
         *   2. 此时构建的 SimpleGrantedAuthority 必须是以 ROLE_ 开头, 例如 ROLE_admin, ROLE_manager.
         *     实现全权限控制的时候使用 @Secured("ROLE_admin") ROLE_是不能省略的。
         */
        return new User(username, "123456",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_admin")));
    }
}
