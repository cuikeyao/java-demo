package com.keyao.no008springretry.demos.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * 一个 Spring注解轻松搞定循环重试功能！
 */
@Service
public class TestRetryService {

    private static final Logger log = LogManager.getLogger(TestRetryService.class);

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public String test(int code) throws Exception {
        log.info("test被调用,时间：" + LocalTime.now());
        if (code == 0) {
            throw new Exception("调用失败啦！");
        }
        log.info("test被调用,真材实料！");

        return "200";
    }

    // 重试次数完成还是抛出异常则调用此方法
    @Recover
    public String recover(Exception e, int code) {
        log.info("回调方法执行！！！！");
        log.info("{}", e.getMessage());
        return "400";
    }
}
