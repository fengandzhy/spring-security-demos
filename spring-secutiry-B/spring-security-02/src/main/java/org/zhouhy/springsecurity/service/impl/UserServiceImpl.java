package org.zhouhy.springsecurity.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhouhy.springsecurity.dao.UserDao;
import org.zhouhy.springsecurity.domain.SysRole;
import org.zhouhy.springsecurity.domain.SysUser;
import org.zhouhy.springsecurity.service.RoleService;
import org.zhouhy.springsecurity.service.UserService;


import java.util.ArrayList;
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void save(SysUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

    /**
     * 认证业务
     * @param username 用户在浏览器输入的用户名
     * @return UserDetails 是springsecurity自己的用户对象
     * @throws UsernameNotFoundException
     *
     * 做数据库验证首先有三点
     * 1. 必须要有实现了UserDetailsService接口的类
     * 2. loadUserByUsername 在这方法中得到用户名和密码以及权限信息
     * 3. 返回一个实现了UserDetails接口的类
     * 4. 另外要在spring-security.xml 做出一些配置来<security:authentication-provider user-service-ref="userServiceImpl">
     * 5. 如果要加密首先在在spring-security.xml中引入BCryptPasswordEncoder 并在security:authentication-provider 做出修改
     * 6. 在创建和修改user的地方也要做相应的修改
     * 7. 关于这个csrf的这个，首先在spring-security.xml开启这个csrf检查
     * 8. 再相关页面先引入相应的标签<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
     * 9. 在相应的form表单里加入<security:csrfInput/>
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = null;
        try {
            sysUser = userDao.findByName(username);
            if(sysUser == null){
                return null;
            }
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for(SysRole role:sysUser.getRoles()){
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
                authorities.add(simpleGrantedAuthority);
            }
            /**将我们自己的sysUser对象翻译成springsecurity认识的userdetails对象
             * 下面四个boolean值只要有一个就不能用
             * 1. 账户是否可用
             * 2. 账户是否失效
             * 3. 密码是否失效
             * 4. 账户是否锁定
             */
            UserDetails userDetails = new User(username,sysUser.getPassword(),
                    sysUser.getStatus()==1,
                    true,
                    true,
                    true,
                    authorities);
            return userDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
