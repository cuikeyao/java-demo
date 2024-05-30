package com.keyao.demo;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class SpringCacheRedisApplicationTests {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    void testStringRedisTemplate() {
        stringRedisTemplate.opsForValue().set("hello", "world");
    }

}
