package com.cks.sso.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cks.sso.mapper.UserMapper;
import com.cks.sso.pojo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 用户登录
     *
     * @param phone 手机号
     * @param code  验证码
     * @return
     */
    public String login( String phone, String code) {
        String redisKey = "CHECK_CODE_" + phone;
        boolean isNew = false;


        //校验验证码
        String redisData = this.redisTemplate.opsForValue().get(redisKey);
        if (!StringUtils.equals(code, redisData)) {
            return null; //验证码错误
        }

        //验证码在校验完成后，需要废弃
        this.redisTemplate.delete(redisKey);

        //mp提供的查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", phone);
        //查询数据库
        User user = this.userMapper.selectOne(queryWrapper);
        if (null == user) {
            //需要注册该用户
            user = new User();
            user.setMobile(phone);
            user.setPassword(DigestUtils.md5Hex("123456"));
            //注册新用户
            this.userMapper.insert(user);
            isNew = true;
            log.info("新增新用户：[{}]", JSON.toJSONString(user));
        }
        //配置生成token参数
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", user.getId());

        // 生成token
        String token = Jwts.builder()
                .setClaims(claims) //payload，存放数据的位置，不能放置敏感数据，如：密码等
                .signWith(SignatureAlgorithm.HS256, secret) //设置加密方法和加密盐
                .setExpiration(new DateTime().plusHours(12).toDate()) //设置过期时间，12小时后过期
                .compact();

        try {
            //rocketMQ发送用户登录成功的消息
            Map<String,Object> msg = new HashMap<>();
            msg.put("id", user.getId());
            msg.put("date", System.currentTimeMillis());
            //msg转换成json并发送
            this.rocketMQTemplate.convertAndSend("tanhua-sso-login", msg);
            log.info("mq消息发送成功:[]",msg);
        } catch (MessagingException e) {
            log.error("MQ发送消息失败！", e);
        }

        return token + "|" + isNew;
    }
}
