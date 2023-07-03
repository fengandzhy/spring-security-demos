package org.frank.spring.security.jdbc.services;


import org.frank.spring.security.jdbc.entities.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SysUserService {
    
    private JdbcTemplate jdbcTemplate;

    public SysUser getSysUserByUsernameOrMobile(String usernameOrMobile) throws EmptyResultDataAccessException {
        String sql = "select u.*, r.role_name, p.permit from sys_user u join sys_user_role ur on u.id = ur.user_id " +
                "             join sys_role r on r.id = ur.role_id " +
                " join sys_role_permission rp on r.id = rp.role_id " +
                " join sys_permission p on rp.permission_id = p.id" +
                " where u.username = ? or u.mobile = ? ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, usernameOrMobile, usernameOrMobile);
        SysUser sysUser = null;
        if(null != list && list.size() > 0) {
            sysUser = new SysUser();
            Map<String, Object> map = list.get(0);
            sysUser.setId((Integer)map.get("id"));
            sysUser.setUsername((String)map.get("username"));
            sysUser.setPassword((String)map.get("password"));
            sysUser.setMobile((String)map.get("mobile"));

            Set<String> roles = new HashSet<>();
            Set<String> permissions = new HashSet<>();
            // 是设置 角色
            list.forEach(rows -> {
                roles.add((String)rows.get("role_name"));
                permissions.add((String) rows.get("permit"));
            });
            sysUser.setRoles(roles);
            sysUser.setPermissions(permissions);
        }
        return sysUser;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
