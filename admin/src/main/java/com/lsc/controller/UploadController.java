package com.lsc.controller;

import com.lsc.domain.Result;
import com.lsc.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Classname UploadController
 * @Description
 * @Date 2023/1/9 10:14
 * @Created by linmour
 */
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    public Result Upload(MultipartFile img){
        return uploadService.upload(img);
    }
}
