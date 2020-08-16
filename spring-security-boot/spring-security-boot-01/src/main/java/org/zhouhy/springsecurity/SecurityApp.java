package org.zhouhy.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("org.zhouhy.springsecurity.mapper")
public class SecurityApp {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApp.class,args);
    }
}
