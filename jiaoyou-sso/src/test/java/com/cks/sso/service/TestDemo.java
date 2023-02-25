package com.cks.sso.service;

import com.cks.sso.SpringBootSsoApplication;
import com.cks.sso.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : [CKSRemote]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2023/2/22 17:12]
 * @updateUser : [CKSRemote]
 */
@SpringBootTest(classes = SpringBootSsoApplication.class)
@RunWith(SpringRunner.class)
public class TestDemo {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test01() {
        // 数据类型 5
        // String
        redisTemplate.opsForValue().set("name","zs");
        String name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
        redisTemplate.delete("name");

        //Hash
        redisTemplate.opsForHash().put("user","name","zs");
        redisTemplate.opsForHash().put("user","age","15");
        redisTemplate.opsForHash().delete("user","name","age");
        //List
        redisTemplate.opsForValue().set("user","name",10000);
        //Set
        redisTemplate.opsForSet().add("myset","zs","lisi");
        //ZSet
        redisTemplate.opsForZSet().add("myzset","zs",100);
        redisTemplate.opsForZSet().add("myzset","lisi",1000);


        // 超时
        redisTemplate.opsForValue().set("name","zs",10, TimeUnit.SECONDS);

        //   判断
        Boolean myset = redisTemplate.hasKey("myset");

    }

}
