package org.zhouhy.jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.zhouhy.jwt.config.RsaKeyProperties;

@SpringBootApplication
@MapperScan("org.zhouhy.jwt.mapper")
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ServerApp {
    public static void main(String[] args) {
        
    }
}
