package com.cks.sso.controller;

import com.cks.sso.service.UserService;
import com.cks.sso.vo.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param param
     * @return
     */
    @PostMapping("loginVerification")
    public ResponseEntity<Object> login(@RequestBody  Map<String,String> param){
        try {
            String phone = param.get("phone");
            //验证码
            String code = param.get("verificationCode");

            String data = this.userService.login(phone, code);

            if(StringUtils.isNotEmpty(data)){
                //登录成功
                Map<String, Object> result = new HashMap<>(2);

                String[] ss = StringUtils.split(data, '|');

                result.put("token", ss[0]);
                //"true" 为string，true为boolean类型，下面转换成boolean
                result.put("isNew", Boolean.valueOf(ss[1]));
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ErrorResult errorResult = ErrorResult.builder().errCode("000002").errMessage("登录失败！").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
    }

    /**
     * 完善个人信息
//     * @param gender 性别 man or woman
//     * @param nickname 昵称
//     * @param birthday 出生日期
//     * @param city 城市
//     * @param header 用户头像
     * @return: org.springframework.http.ResponseEntity<java.lang.Object>
     */
    @PostMapping("loginReginfo")
    public ResponseEntity<Object> loginRegInfo(@RequestBody Map<String,String> param,
                                               @RequestHeader("Authorization") String token){

        log.info("接收数据：[{}],token：[{}]",param,token);
        return null;
    }


    @PostMapping("loginReginfo/head")
    public ResponseEntity<Object> saveUserLogo(@RequestBody MultipartFile file,
                                               @RequestHeader("Authorization") String token){
        log.info("：[{}]：[{}]",file,token);

        return null;
    }

}
