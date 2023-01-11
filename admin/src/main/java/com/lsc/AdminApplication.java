package com.lsc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname AdminApplication
 * @Description
 * @Date 2023/1/6 13:17
 * @Created by linmour
 */



@SpringBootApplication
@MapperScan("com.lsc.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}