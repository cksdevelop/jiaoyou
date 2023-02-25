package com.cks.sso.controller;

//import com.cks.sso.service.SmsService;
import com.cks.sso.service.SmsVService;
import com.cks.sso.vo.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController //==@Controller +@ResponseBody
@RequestMapping("/user")
@Slf4j
public class SmsController {

    @Autowired
    private SmsVService service;
    /**
        发送短信验证码接口

      * @param param
     * @return
     */

    @PostMapping("/login")
    public ResponseEntity<ErrorResult> sendCheckCode(@RequestBody Map<String, String> param) {

        ErrorResult errorResult = null;
        String phone = param.get("phone");
        try {
             errorResult = service.createClient(phone);
            if (null == errorResult) {
                return ResponseEntity.ok(null);
            }
        } catch (Exception e) {
            log.error("发送短信验证码失败~ phone = " + phone, e);
        }

        errorResult = ErrorResult.builder().errCode("000002").errMessage("短信验证码发送失败！").build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
    }





    public ResponseEntity<String>   save() {
        try {
            //int i = 1/0;// "success"
          return  ResponseEntity.ok("succeee"); // 200 + 数据
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

}
