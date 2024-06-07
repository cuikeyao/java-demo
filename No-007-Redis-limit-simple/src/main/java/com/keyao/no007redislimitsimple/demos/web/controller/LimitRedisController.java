package com.keyao.no007redislimitsimple.demos.web.controller;

import com.keyao.no007redislimitsimple.demos.web.aop.RedisLimit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringBoot如何用redis限流
 */
@RestController
public class LimitRedisController {

    private static final Logger log = LogManager.getLogger(LimitRedisController.class);

    /**
     * 基于Redis AOP限流
     */
    @RequestMapping("/get")
    @RedisLimit(key = "redis-limit:get", permitsPerSecond = 2, expire = 1, msg = "当前排队人数较多，请稍后再试！")
    public String get() {
        log.info("限流成功。。。");
        return "ok";
    }

}
