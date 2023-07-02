package org.frank.security.authority.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
public class UserController {

    /**
     * This annotation can be specified on a class or on method(s). Specifying it
     *  at a class level means that it applies to all the methods in the class.
     *  Specifying it on a method means that it is applicable to that method only.
     *
     * @RolesAllowed  可以写的值 ROLE_admin  admin
     *
     * he <code>Secured</code> annotation is used to define a list of security configuration
     *  attributes for business methods.
     * @Secured  只支持ROLE_admin  尽量用在service中。
     */
    @RequestMapping
    // @RolesAllowed("admin")
    // @Secured({"ROLE_admin", "ROLE_finance"})
    // @PreAuthorize("hasAuthority('user:list')")
    public Object get() {
        return "/index.html";
    }


    @ResponseBody
    @RequestMapping("/list")
    // @PreAuthorize("hasRole('ROLE_admin')")
    //@RolesAllowed({"admin", "finance", "administration "})
    // @PreAuthorize("hasRole('admin') or hasRole('finance')")
    // @PreAuthorize("hasRole('admin') and hasRole('finance')")  //表是同时拥有这两个角色才能访问
    // @PreAuthorize("hasAnyRole('admin', 'finance')")  //与上面的属性形式一样
    // @PreAuthorize("hasAuthority('user:list')") //3个用户都有这个权限
    public Object list() {
        return "list";
    }

    @ResponseBody
    @RequestMapping("/delete")
    // @PreAuthorize("hasAuthority('user:delete')") //只有jack
    public Object delete() {
        return "delete";
    }

    @ResponseBody
    @RequestMapping("/export")
    // @PreAuthorize("hasAuthority('user:export')") // 只有rose, jack
    public Object export() {
        return "export";
    }
}
