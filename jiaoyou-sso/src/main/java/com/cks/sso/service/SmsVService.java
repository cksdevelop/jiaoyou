// This file is auto-generated, don't edit it. Thanks.
package com.cks.sso.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.*;
import com.cks.sso.config.AliyunSMSConfig;
import com.cks.sso.vo.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;

//@EnableAutoConfiguration
@Service
@Slf4j
public class SmsVService {
    @Autowired
    private AliyunSMSConfig aliyunSMSConfig;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public ErrorResult createClient(String phone) {


        ErrorResult errorResult = sendCheckCode(phone);

        return errorResult;
    }

    public String sendPhone(String phone) {
        //生成code
//        String code = RandomUtils.nextInt(100000, 999999) + "";
        String code = "123456";
        try {
            com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                    // 必填，您的 AccessKey ID
                    .setAccessKeyId(aliyunSMSConfig.getAccessKeyId())
                    // 必填，您的 AccessKey Secret
                    .setAccessKeySecret(aliyunSMSConfig.getAccessKeySecret());
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";
            com.aliyun.dysmsapi20170525.Client client = new com.aliyun.dysmsapi20170525.Client(config);

            // 工程代码泄露可能会导致AccessKey泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，
            // 建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
            com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                    .setSignName(aliyunSMSConfig.getSignName())
                    .setTemplateCode(aliyunSMSConfig.getTemplateCode())
                    .setTemplateParam("{\"code\":" + code + "}")
                    .setPhoneNumbers(phone);
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();

            // 复制代码运行请自行打印 API 的返回值

            //发送验证码
            log.info("发送用户:{}，验证码为:{}",phone,code);
            SendSmsResponse sendSmsResponse =null;
//            client.sendSmsWithOptions(sendSmsRequest, runtime);
//            Integer smsCode = sendSmsResponse.getStatusCode();
            if (true) {
                //发送成功
//                log.debug(sendSmsResponse.toString());
                return code;
            }
        } catch (Exception e) {
            log.info("验证码发送失败");
        }
        //发送失败返回空CODE
        return code;
    }

    public ErrorResult sendCheckCode(String phone) {
        String redisKey = "CHECK_CODE_" + phone;
        //先判断该手机号的验证码是否失效
        if (this.redisTemplate.hasKey(redisKey)) {
            return ErrorResult
                    .builder()
                    .errCode(HttpStatus.ALREADY_REPORTED.toString())
                    .errMessage("验证码还未失效")
                    .build();
        }
        String code = this.sendPhone(phone);
        if (StringUtils.isEmpty(code)){
            return ErrorResult
                    .builder()
                    .errCode(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).toString())
                    .errMessage("验证码发送失败！")
                    .build();
        }

        //验证码发送成功，将验证码保存到redis中
        this.redisTemplate.opsForValue().set(redisKey,code,Duration.ofMinutes(5));
        return null;
    }

}