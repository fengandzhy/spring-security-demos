package org.zhouhy.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 1. 加入了maven依赖,什么也不动它自动回有一个login 页面,用户名是user 密码是控制台出出的UUID字符串. UserDetailsServiceAutoConfiguration
 * 2. 挡在application.properties做了以下配置时
 * */
@SpringBootApplication
public class FormLoginApp {
    public static void main(String[] args) {
        SpringApplication.run(FormLoginApp.class,args);
    }
}
