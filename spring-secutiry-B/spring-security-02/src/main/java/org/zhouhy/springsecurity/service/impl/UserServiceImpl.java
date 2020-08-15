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
     * 1. 首先关于父容器和子容器, 在本例中 spring-mvc.xml 是DispatcherServlet加载的,它是子容器可以访问父容器中的对象也可以被http请求访问到
     * 就是说在这里面注册的bean,可以被http请求访问到,这里面注册的bean都是controller,所以http请求可以到达这里
     * 2. applicationContext.xml 是ContextLoaderListener加载的,它是父容器,父容器对象不能被http请求访问到
     * 在这里祖册的bean是service和dao,这是无法被访问到的
     * 3. 比方说在在applicationContext.xml 中开启<mvc:annotation-driven/> 则在service或者dao里面才会有@Transactional注解支持有效
     * 如果<mvc:annotation-driven/>开启在了spring-mvc.xml中在service或者dao@Transactional失效
     * 4. 同理要想实现权限控制 下面的这段代码如果加在了父容器里面,则要在service或者dao中控制,如果加在了子容器里面则controller里面控制
     * <security:global-method-security
     *             secured-annotations="enabled"
     *             pre-post-annotations="enabled"
     *             jsr250-annotations="enabled"/>
     * 5.然后再在controller上加入 @Secured({"ROLE_ORDER","ROLE_ADMIN"}) 就保证了只有ROLE_ORDER 和 ROLE_ADMIN 权限的人才可以访问这个controller
     * 6.作为权限控制,首先要给他们每一个人都有一个权限确保他们能通过spring security的校验 ROLE_USER 然后在此基础上给他们分配不同权限去访问不同资源
     * 例如ROLE_ORDER 只能访问订单 ROLE_PRODUCT 只能访问产品
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
