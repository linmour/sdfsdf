package com.lsc.service.impl;

import com.google.gson.Gson;
import com.lsc.domain.Result;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.exception.SystemException;
import com.lsc.service.UploadService;
import com.lsc.utils.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Classname UploadServiceImpl
 * @Description
 * @Date 2022/12/15 9:26
 * @Created by linmour
 */
@Component
@ConfigurationProperties(prefix = "oss")
@Data
public class UploadServiceImpl implements UploadService {
    @Override
    public Result upload(MultipartFile img) {

        //获取源文件名字，判断文件类型
        String originalFilename = img.getOriginalFilename();
        if (!originalFilename.endsWith(".png")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //生成文件名
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img,filePath);
        return Result.okResult(url);

    }

    //...生成上传凭证，然后准备上传
    private String accessKey;
    private String secretKey;
    //存储空间名
    private String bucket ;
    //外链
    private String cdn;


    @Test
    public String uploadOss(MultipartFile img,String filePath) {

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion() );
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);


        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;

        try {
            //直接获取传进来的文件流
            InputStream fis = img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fis,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return cdn+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

        return "66666666666";
    }
}
