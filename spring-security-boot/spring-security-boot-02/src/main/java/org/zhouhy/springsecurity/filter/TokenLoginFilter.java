package org.zhouhy.springsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zhouhy.springsecurity.config.RsaKeyProperties;

import org.zhouhy.springsecurity.domain.SysRole;
import org.zhouhy.springsecurity.domain.SysUser;
import org.zhouhy.springsecurity.utils.JwtUtils;

import javax.servlet.FilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 地址是/login 请求方式是POST才会被这个TokenLoginFilter截取
 *
 * */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private RsaKeyProperties prop;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop){
        this.authenticationManager=authenticationManager;
        this.prop = prop;
    }


    /**
     * 接收并解析用户凭证，出現错误时，返回json数据前端
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res){
        try {
            SysUser user = new ObjectMapper().readValue(req.getInputStream(), SysUser.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        } catch (Exception e) {
            try {
                //如果认证失败，提供自定义json格式异常
                res.setContentType("application/json;charset=utf-8");
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = res.getWriter();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
                map.put("message", "账号或密码错误！");
                out.write(new ObjectMapper().writeValueAsString(map));
                out.flush();
                out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }


    /**
     * 用户登录成功后，生成token,并且返回json数据给前端
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,FilterChain chain, Authentication auth) {
        //得到当前认证的用户对象
        SysUser user = new SysUser();
        user.setUsername(auth.getName());
        user.setRoles((List<SysRole>) auth.getAuthorities());
        //json web token构建
        String token = JwtUtils.generateTokenExpireInMinutes(user, prop.getPrivateKey(), 24*60);
        //返回token
        res.addHeader("Authorization1", "Bearer " + token);
        try {
            //登录成功時，返回json格式进行提示
            res.setContentType("application/json;charset=utf-8");
            res.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = res.getWriter();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", HttpServletResponse.SC_OK);
            map.put("message", "登陆成功！");
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
