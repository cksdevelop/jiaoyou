package com.cks.sso.service;

import com.cks.sso.SpringBootSsoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author : [CKSRemote]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2023/2/25 16:54]
 * @updateUser : [CKSRemote]
 */
@Slf4j
@SpringBootTest(classes = SpringBootSsoApplication.class)
@RunWith(SpringRunner.class)
public class OSSTest {
    @Autowired
    private PicUploadService service;
    @Test
    public void testFile(){
        String objectName = "test/exampjjjject.txt";
//        service.upload(new File("D:\\TestSource\\hj.jpg"));
    }
}
