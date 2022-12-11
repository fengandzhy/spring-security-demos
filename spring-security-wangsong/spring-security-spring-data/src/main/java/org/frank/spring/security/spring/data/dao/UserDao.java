package org.frank.spring.security.spring.data.dao;

import org.frank.spring.security.spring.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
