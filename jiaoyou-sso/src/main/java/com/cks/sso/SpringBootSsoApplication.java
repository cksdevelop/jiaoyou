package com.cks.sso;

import com.cks.sso.config.AliyunSMSConfig;
import com.cks.sso.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@EnableConfigurationProperties(AliyunSMSConfig.class)
@MapperScan("com.cks.sso.mapper") //设置mapper接口的扫描包
@SpringBootApplication
public class SpringBootSsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSsoApplication.class,args);
    }
}