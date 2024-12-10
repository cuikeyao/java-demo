package com.keyao.no007redislimitsimple.demos.web.controller;

import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.lock.executor.RedissonLockExecutor;
import com.keyao.no007redislimitsimple.demos.web.aop.RedisLimit;
import com.keyao.no007redislimitsimple.demos.web.aop.SlidingWindowRateLimiter;
import com.keyao.no007redislimitsimple.demos.web.exception.RedisLimitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * SpringBoot如何用redis限流
 */
@Slf4j
@RestController
public class LimitRedisController {
    @Autowired
    private SlidingWindowRateLimiter slidingWindowRateLimiter;

    @Autowired
    private LockTemplate lockTemplate;

    /**
     * 注解方式 基于Redis手动实现 1秒钟最多2次请求
     */
    @RequestMapping("/get")
    @RedisLimit(key = "redis-limit:get", permitsPerSecond = 2, expire = 1, msg = "当前排队人数较多，请稍后再试！")
    public String get() {
        log.info("限流成功。。。");
        return "ok";
    }

    /**
     * 注解方式 基于Lock4j 1秒钟最多1次请求
     */
    @RequestMapping("/get1")
    @Lock4j(expire = 1000,acquireTimeout = 1000, autoRelease = false)
    public String get1() {
        log.info("Lock4j 限流成功。。。");
        return "ok";
    }

    /**
     * 手动上锁自动解锁 基于Redission窗口限流   1秒钟最多2次请求
     * @return
     */
    @RequestMapping("/get2")
    public String get2() {
        Boolean access = slidingWindowRateLimiter.tryAcquire("get2", 2, 1);
        if (!access) {
            throw new RedisLimitException("当前排队人数较多，请稍后再试！");
        }
        log.info("Lock4j 限流成功。。。");
        return "ok";
    }

    /**
     * 手动上锁 自动+手动解锁 基于Lock4j
     */
    @RequestMapping("/get3")
    public String get3() {
        // 获取锁
        LockInfo lockInfo = lockTemplate.lock("get3", 10000L, 1000L, RedissonLockExecutor.class);
        if (Objects.isNull(lockInfo)) {
            throw new RuntimeException("业务处理中,请稍后再试......");
        }
        // 获取锁成功，处理业务
        try {
            Thread.sleep(5000);
            // 处理业务逻辑
            log.info("Lock4j 限流成功。。。");
            return "ok";
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //释放锁
            lockTemplate.releaseLock(lockInfo);
        }
    }

}
