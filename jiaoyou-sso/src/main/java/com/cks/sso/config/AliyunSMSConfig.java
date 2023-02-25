package com.cks.sso.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "aliyun.sms")
@Data
public class AliyunSMSConfig {

//    private String regionId;
    private String accessKeyId;
    private String accessKeySecret;
    private String templateCode;
    private String signName;
    private String phoneNumbers;
}