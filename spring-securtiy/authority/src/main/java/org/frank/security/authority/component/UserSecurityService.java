package org.frank.security.authority.component;

import org.frank.security.authority.entities.SysUser;
import org.frank.security.authority.services.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class UserSecurityService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserSecurityService.class);
    @Resource
    private SysUserService sysUserService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户名或者电话：" + username);
        SysUser sysUser = null;
        try{
            sysUser = sysUserService.getSysUserByUsernameOrMobile(username);
        }catch (EmptyResultDataAccessException exception) {  //没有对应的用户名异常
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        if(null == sysUser) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }else {
            /**
             * User第一参数是：用户名
             *     第二个参数是：pssword, 是从数据库查出来的
             *     第三个参数是: 权限
             */
            User user =  null;
            try{
                user = new User(username,
                        sysUser.getPassword(),
//                        getAuthorities(sysUser.getRoles()));
                        getMethodsAuthorities(sysUser.getPermissions()));
            }catch (InternalAuthenticationServiceException exception) {
                throw exception;  // 在此处，将异常接着往外抛，抛给AuthenticationFailureHandler处理
            }
            return user;
        }
    }

    // [new SimpleGrantedAuthority("user:list"), new SimpleGrantedAuthority("user:add")]
    private List<SimpleGrantedAuthority> getMethodsAuthorities(Set<String> permissions) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        if(null != permissions && permissions.size() > 0){
            permissions.forEach(permission -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission); // user:list dept:list
                list.add(simpleGrantedAuthority);
            });
        }
        return list;
    }

    // [new SimpleGrantedAuthority("ROLE_admin"), new SimpleGrantedAuthority("ROLE_xxx")]
    // 封装用户角色权限
    private List<SimpleGrantedAuthority> getAuthorities(Set<String> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        if(null != roles && roles.size() > 0){
            roles.forEach(role -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + role);
                list.add(simpleGrantedAuthority);
            });
        }
        return list;
    }
}
