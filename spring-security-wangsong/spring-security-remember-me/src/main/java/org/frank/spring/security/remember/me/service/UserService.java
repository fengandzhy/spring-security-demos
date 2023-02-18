package org.frank.spring.security.remember.me.service;


import org.frank.spring.security.remember.me.dao.UserDao;
import org.frank.spring.security.remember.me.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(null == user){
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
}
