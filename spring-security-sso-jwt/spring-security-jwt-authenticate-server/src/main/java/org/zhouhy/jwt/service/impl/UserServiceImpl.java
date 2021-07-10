package org.zhouhy.jwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zhouhy.jwt.domain.UserPojo;
import org.zhouhy.jwt.mapper.UserMapper;
import org.zhouhy.jwt.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPojo userPojo = mapper.queryByUserName(username);
        return userPojo;
    }
}
