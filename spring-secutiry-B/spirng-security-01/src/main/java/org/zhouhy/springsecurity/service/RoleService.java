package org.zhouhy.springsecurity.service;


import org.zhouhy.springsecurity.domain.SysRole;

import java.util.List;

public interface RoleService {

    void save(SysRole role);

    List<SysRole> findAll();

}
