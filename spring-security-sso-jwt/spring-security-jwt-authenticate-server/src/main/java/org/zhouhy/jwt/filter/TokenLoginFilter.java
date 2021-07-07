package org.zhouhy.jwt.filter;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zhouhy.jwt.config.RsaKeyProperties;
import org.zhouhy.jwt.domain.RolePojo;
import org.zhouhy.jwt.domain.UserPojo;
import org.zhouhy.jwt.utils.JsonUtils;
import org.zhouhy.jwt.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义认证过滤器
 * 
 * */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {    
    private AuthenticationManager authenticationManager;
    private RsaKeyProperties rsaKeyProperties;
    
    public TokenLoginFilter(AuthenticationManager authenticationManager,RsaKeyProperties rsaKeyProperties){
        this.authenticationManager = authenticationManager;
        this.rsaKeyProperties = rsaKeyProperties;
    }
    
    /**
     * 这个方法来自于它的父类
     * */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        try {
            UserPojo sysUser = JsonUtils.toBean(request.getInputStream(),UserPojo.class);
            UsernamePasswordAuthenticationToken authRequest = 
                    new UsernamePasswordAuthenticationToken(sysUser.getUsername(),sysUser.getPassword());
            return authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                Map resultMap = new HashMap();
                resultMap.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                resultMap.put("msg", "用户名或密码错误！");
                out.write(JsonUtils.toString(resultMap));
                out.flush();
                out.close();
            } catch (Exception outEx) {
                outEx.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 这个方法来自于它的祖父类
     * 
     * */
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain chain, Authentication authResult){
        UserPojo user = new UserPojo();
        user.setUsername(authResult.getName());
        user.setRoles((List<RolePojo>)authResult.getAuthorities());
        String token = JwtUtils.generateTokenExpireInMinutes(user, rsaKeyProperties.getPrivateKey(),24*60);
        response.setHeader("Authorization","Bearer "+token);
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            Map resultMap = new HashMap();
            resultMap.put("code", HttpServletResponse.SC_OK);
            resultMap.put("msg", "认证通过！");
            out.write(JsonUtils.toString(resultMap));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
