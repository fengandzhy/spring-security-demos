package org.frank.spring.security.spring.data.dao;

import org.frank.spring.security.spring.data.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role,Long> {
}
