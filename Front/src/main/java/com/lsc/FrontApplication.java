package com.lsc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Classname FrontApplication
 * @Description
 * @Date 2022/12/10 15:52
 * @Created by linmour
 */
@SpringBootApplication
@MapperScan("com.lsc.mapper")
@EnableScheduling
@EnableSwagger2
public class FrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class,args);
    }
}
