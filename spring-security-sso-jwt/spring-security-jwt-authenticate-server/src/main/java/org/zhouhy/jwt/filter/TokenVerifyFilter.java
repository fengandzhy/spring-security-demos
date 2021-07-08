package org.zhouhy.jwt.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.zhouhy.jwt.config.RsaKeyProperties;

public class TokenVerifyFilter extends BasicAuthenticationFilter {
    
    private RsaKeyProperties prop;
    
    public TokenVerifyFilter(AuthenticationManager authenticationManager,RsaKeyProperties prop) {
        super(authenticationManager);
        this.prop = prop;
    }
    
    
}
