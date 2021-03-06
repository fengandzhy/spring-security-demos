package org.zhouhy.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zhouhy.springsecurity.dao.UserDao;
import org.zhouhy.springsecurity.entities.User;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(null == user){
            throw new UsernameNotFoundException("用户不存在");
        }
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return user;
    }
}
