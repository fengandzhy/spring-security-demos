package org.zhouhy.springsecurity.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhouhy.springsecurity.dao.UserDao;
import org.zhouhy.springsecurity.domain.SysRole;
import org.zhouhy.springsecurity.domain.SysUser;
import org.zhouhy.springsecurity.service.RoleService;
import org.zhouhy.springsecurity.service.UserService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;


    public void save(SysUser user) {
        userDao.save(user);
    }

    public List<SysUser> findAll() {
        return userDao.findAll();
    }

    public Map<String, Object> toAddRolePage(Integer id) {
        List<SysRole> allRoles = roleService.findAll();
        List<Integer> myRoles = userDao.findRolesByUid(id);
        Map<String, Object> map = new HashMap<>();
        map.put("allRoles", allRoles);
        map.put("myRoles", myRoles);
        return map;
    }

    public void addRoleToUser(Integer userId, Integer[] ids) {
        userDao.removeRoles(userId);
        for (Integer rid : ids) {
            userDao.addRoles(userId, rid);
        }
    }
}
