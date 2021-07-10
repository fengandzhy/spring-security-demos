package org.zhouhy.jwt.filter;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.zhouhy.jwt.config.RsaKeyProperties;
import org.zhouhy.jwt.domain.Payload;
import org.zhouhy.jwt.domain.UserPojo;
import org.zhouhy.jwt.utils.JsonUtils;
import org.zhouhy.jwt.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class TokenVerifyFilter extends BasicAuthenticationFilter {
    
    private RsaKeyProperties prop;
    
    public TokenVerifyFilter(AuthenticationManager authenticationManager,RsaKeyProperties prop) {
        super(authenticationManager);
        this.prop = prop;
    }
    
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(header==null || !header.startsWith("Bearer ")){
            chain.doFilter(request,response);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            Map resultMap = new HashMap();
            resultMap.put("code", HttpServletResponse.SC_FORBIDDEN);
            resultMap.put("msg", "请登录！");
            out.write(JsonUtils.toString(resultMap));
            out.flush();
            out.close();
        }else{
            String token = header.replace("Bearer ","");
            Payload<UserPojo> payload = JwtUtils.getInfoFromToken(token,prop.getPublicKey(), UserPojo.class);
            UserPojo userPojo = payload.getUserInfo();
            if(userPojo!=null){
                UsernamePasswordAuthenticationToken authResult = 
                        new UsernamePasswordAuthenticationToken(userPojo.getUsername(),null,userPojo.getAuthorities())
                SecurityContextHolder.getContext().setAuthentication(authResult);
                chain.doFilter(request,response);
            }
        }
    }
}
