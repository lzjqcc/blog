package com.lzj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by li on 17-8-6.
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.lzj.dao")
@EnableRedisHttpSession
public class Application {
    public static void main(String[]args){
        SpringApplication.run(Application.class,args);
    }
}
