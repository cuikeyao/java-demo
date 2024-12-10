package com.keyao.no007redislimitsimple.demos.web.handler;

import com.baomidou.lock.LockFailureStrategy;
import com.keyao.no007redislimitsimple.demos.web.exception.RedisLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义获取锁失败策略，抛出 {@link RedisLimitException} 异常
 */
@Component
@Slf4j
public class CustomLockFailureStrategy implements LockFailureStrategy {

    @Override
    public void onLockFailure(String key, Method method, Object[] arguments) {
        log.info("[onLockFailure][线程:{} 获取锁失败，key:{} 获取失败:{} ]", Thread.currentThread().getName(), key, arguments);
        throw new RedisLimitException("当前排队人数较多，请稍后再试！");
    }
}
