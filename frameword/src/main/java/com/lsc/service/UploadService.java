package com.lsc.service;

import com.lsc.domain.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Classname UploadService
 * @Description
 * @Date 2022/12/15 9:25
 * @Created by linmour
 */
public interface UploadService {
    Result upload(MultipartFile img);

}
