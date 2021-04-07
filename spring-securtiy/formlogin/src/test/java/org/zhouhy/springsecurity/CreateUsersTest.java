package org.zhouhy.springsecurity;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.zhouhy.springsecurity.dao.UserDao;
import org.zhouhy.springsecurity.entity.Role;
import org.zhouhy.springsecurity.entity.User;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateUsersTest {

    @Autowired
    UserDao userDao;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void saveUser(){
        User u1 = new User();
        u1.setUsername("frank");
        u1.setPassword(passwordEncoder.encode("123456"));
        u1.setAccountNonExpired(true);
        u1.setAccountNonLocked(true);
        u1.setCredentialsNonExpired(true);
        u1.setEnabled(true);
        List<Role> rs1 = new ArrayList<>();
        Role r1 = new Role();
        r1.setName("ROLE_user");
        r1.setNameZh("普通用户");
        rs1.add(r1);
        u1.setRoles(rs1);
        userDao.save(u1);
    }
}
