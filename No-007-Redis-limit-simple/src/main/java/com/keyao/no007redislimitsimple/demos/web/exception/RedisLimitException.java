package com.keyao.no007redislimitsimple.demos.web.exception;


/**
 * Redis限流自定义异常
 * @date 2023/3/10 21:43
 */
public class RedisLimitException extends RuntimeException{
    public RedisLimitException(String msg) {
        super( msg );
    }
}
