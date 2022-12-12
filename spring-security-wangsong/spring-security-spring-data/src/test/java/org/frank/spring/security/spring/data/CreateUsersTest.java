package org.frank.spring.security.spring.data;

import org.frank.spring.security.spring.data.dao.RoleDao;
import org.frank.spring.security.spring.data.dao.UserDao;
import org.frank.spring.security.spring.data.entities.Role;
import org.frank.spring.security.spring.data.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CreateUsersTest {

    @Autowired
    UserDao userDao;
    
    @Autowired
    RoleDao roleDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void saveUser(){
        User u1 = new User();
        u1.setUsername("sam");
        u1.setPassword(passwordEncoder.encode("123456"));
        u1.setAccountNonExpired(true);
        u1.setAccountNonLocked(true);
        u1.setCredentialsNonExpired(true);
        u1.setEnabled(true);
//        userDao.save(u1);
//        List<Role> rs1 = new ArrayList<>();
        Role r1 = roleDao.findById(1l).get();
//        r1.setName("ROLE_user");
//        r1.setNameZh("普通用户");        
        u1.getRoles().add(r1);
        
        userDao.save(u1);
    }

}
