package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.service.UploadService;
import org.apache.logging.log4j.message.MultiformatMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Classname UploadController
 * @Description
 * @Date 2022/12/15 9:21
 * @Created by linmour
 */
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    public Result upload(MultipartFile img){
        return uploadService.upload(img);
    }
}
