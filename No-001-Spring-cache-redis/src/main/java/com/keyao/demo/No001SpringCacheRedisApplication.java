package com.keyao.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//启动缓存
@EnableCaching
@SpringBootApplication
public class No001SpringCacheRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(No001SpringCacheRedisApplication.class, args);
    }

}
