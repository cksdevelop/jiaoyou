package com.cks.sso.service;

import com.cks.sso.SpringBootSsoApplication;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : [CKSRemote]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2023/2/25 1:09]
 * @updateUser : [CKSRemote]
 */
@Slf4j
@SpringBootTest(classes = SpringBootSsoApplication.class)
@RunWith(SpringRunner.class)
public class JwtTest {
    String secret = "itcast";

    @Test
    public void testCreateToken(){

        Map<String, Object> header = new HashMap<String, Object>();
        header.put(JwsHeader.TYPE, JwsHeader.JWT_TYPE);
        header.put(JwsHeader.ALGORITHM, "HS256");

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("mobile", "1333333333");
        claims.put("id", "2");

        // 生成token
        String jwt = Jwts.builder()
                .setHeader(header)  //header，可省略
                .setClaims(claims) //payload，存放数据的位置，不能放置敏感数据，如：密码等
                .signWith(SignatureAlgorithm.HS256, secret) //设置加密方法和加密盐
                .setExpiration(new Date(System.currentTimeMillis() + 120000)) //设置过期时间，3000 为3秒后过期
                .compact();

        System.out.println(jwt);

    }

    @Test
    public void testDecodeToken(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtb2JpbGUiOiIxMzMzMzMzMzMzIiwiaWQiOiIyIiwiZXhwIjoxNjc3MjU5Mzg1fQ.maGjofMRmgoZ7HQR9J_Ru_Y9vD7N9fzpnt4xuREwWkk";
        try {
            // 通过token解析数据
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(body); //{mobile=1333333333, id=2, exp=1605513392}
        } catch (ExpiredJwtException e) {
            System.out.println("token已经过期！");
        } catch (Exception e) {
            System.out.println("token不合法！");
        }
    }

}
