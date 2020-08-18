package org.zhouhy.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.zhouhy.springsecurity.config.RsaKeyProperties;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("org.zhouhy.springsecurity.mapper")
@EnableConfigurationProperties(RsaKeyProperties.class)
public class JwtResourceApp {
    public static void main(String[] args) {
        SpringApplication.run(JwtResourceApp.class,args);
    }
}
