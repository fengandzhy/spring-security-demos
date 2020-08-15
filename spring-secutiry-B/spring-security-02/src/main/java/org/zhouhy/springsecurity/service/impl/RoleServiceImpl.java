package org.zhouhy.springsecurity.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhouhy.springsecurity.dao.RoleDao;
import org.zhouhy.springsecurity.domain.SysRole;
import org.zhouhy.springsecurity.service.RoleService;

import java.util.List;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {


    @Autowired
    private RoleDao roleDao;

    public void save(SysRole role) {
        roleDao.save(role);
    }

    public List<SysRole> findAll() {
        return roleDao.findAll();
    }
}
