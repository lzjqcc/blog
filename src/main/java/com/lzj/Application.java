package com.lzj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by li on 17-8-6.
 */
@SpringBootApplication
@MapperScan("com.lzj.dao")
public class Application {
    public static void main(String[]args){
        SpringApplication.run(Application.class,args);
    }
}
