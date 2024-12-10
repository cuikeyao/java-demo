package com.keyao.no021controlleradvice.demos.web.handler;

import com.keyao.no021controlleradvice.demos.web.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalWebExceptionHandler {
    /**
     * 自定义业务异常处理器
     *
     * @param bizException
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result exceptionHandler(ArithmeticException bizException) {
        log.error("bizException occurred.", bizException);
        Result result = new Result();
        result.setMessage(bizException.getMessage());
        result.setSuccess(false);
        return result;
    }
}
