package org.zhouhy.springsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.zhouhy.springsecurity.mapper")
public class ResourceApp {
    public static void main(String[] args) {
        SpringApplication.run(ResourceApp.class,args);
    }
}
