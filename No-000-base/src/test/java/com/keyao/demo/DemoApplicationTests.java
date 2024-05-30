package com.keyao.demo;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class DemoApplicationTests {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    void testStringRedisTemplate() {

    }

}
