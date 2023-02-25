package com.cks.sso.service;

import com.cks.sso.SpringBootSsoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest(classes = SpringBootSsoApplication.class)
@RunWith(SpringRunner.class)
public class FaceEngineServiceTest {

    @Autowired
    private FaceEngineService faceEngineService;

    @Test
    public void testCheckIsPortrait(){
        File file = new File("D:\\TestSource\\face.jpg");
        boolean checkIsPortrait = this.faceEngineService.checkIsPortrait(file);
        log.info("结果{}",checkIsPortrait);
    }
}