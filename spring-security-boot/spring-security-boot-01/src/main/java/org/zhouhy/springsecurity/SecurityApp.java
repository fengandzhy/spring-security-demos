package org.zhouhy.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * 1. 加入tomcat插件启动必须去掉pom中的其他插件, 然后在pom文件中加入
 *         <dependency>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-starter-tomcat</artifactId>
 *         </dependency>
 *
 *         <dependency>
 *             <groupId>org.apache.tomcat.embed</groupId>
 *             <artifactId>tomcat-embed-jasper</artifactId>
 *         </dependency>
 *  2. 关于spring-boot整合spring-security
 *  第一 自己的user类(SysUser)实现接口UserDetails, 然后自己的role类(SysRole)实现接口GrantedAuthority
 *  第二 自己的service类()必须实现接口UserDetailsService 然后实现里面的方法返回一个UserDetails对象
 *  第三 在这个SecurityConfig里面做出相应的改变比方说在这个认证用户来源这里
 * */
@SpringBootApplication
@MapperScan("org.zhouhy.springsecurity.mapper")
public class SecurityApp {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApp.class,args);
    }
}
