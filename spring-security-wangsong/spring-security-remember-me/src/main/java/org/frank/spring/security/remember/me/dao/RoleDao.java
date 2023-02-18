package org.frank.spring.security.remember.me.dao;

import org.frank.spring.security.remember.me.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role,Long> {
}
