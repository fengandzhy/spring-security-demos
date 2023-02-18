package org.frank.spring.security.remember.me.dao;


import org.frank.spring.security.remember.me.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
