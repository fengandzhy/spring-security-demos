package org.zhouhy.springsecurity.service;


import org.zhouhy.springsecurity.domain.SysRole;

import java.util.List;

public interface RoleService {

    public void save(SysRole role);

    public List<SysRole> findAll();

}
