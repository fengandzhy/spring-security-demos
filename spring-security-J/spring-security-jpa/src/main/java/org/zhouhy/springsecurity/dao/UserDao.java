package org.zhouhy.springsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zhouhy.springsecurity.entities.User;

public interface UserDao extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
