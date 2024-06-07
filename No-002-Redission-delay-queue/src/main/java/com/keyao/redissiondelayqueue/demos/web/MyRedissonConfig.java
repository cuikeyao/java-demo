package com.keyao.redissiondelayqueue.demos.web;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: woniu
 * @createTime: 2023-03-11 09:39
 **/

@Configuration
public class MyRedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.56.10:6379").setPassword("123456");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }


    @Bean
    public RBlockingQueue<String> blockingQueue(RedissonClient redissonClient) {
        return redissonClient.getBlockingQueue("TOKEN-RENEWAL");
    }

    @Bean
    public RDelayedQueue<String> delayedQueue(RBlockingQueue<String> blockingQueue,
                                              RedissonClient redissonClient) {
        return redissonClient.getDelayedQueue(blockingQueue);
    }
}

