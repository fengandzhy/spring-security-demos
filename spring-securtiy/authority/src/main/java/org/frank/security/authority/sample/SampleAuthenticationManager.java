package org.frank.security.authority.sample;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class SampleAuthenticationManager implements AuthenticationManager {

    //配置一个简单的用户权限集合
    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //如果用户名和密码一致，则登录成功，这里只做了简单认证
        if (authentication.getName().equals(authentication.getCredentials())) {
            //认证成功，生成已认证Authentication，比未认证多了权限
            return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
        }

        throw new BadCredentialsException("Bad Credentials");

        
    }
}
