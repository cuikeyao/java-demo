package com.keyao.redissiondelayqueue.demos.web;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonDelayQueue {

    @Autowired
    private RDelayedQueue<String> delayedQueue;

    @Autowired
    private  RBlockingQueue<String> blockingQueue;


    @PostConstruct
    public void init() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            while (true) {
                try {
                    String task = blockingQueue.take();
                    log.info("receive delay task:{}", task);
                } catch (Exception e) {
                    log.error("occur error", e);
                }
            }
        });
    }

    public void offerTask(String task, long seconds) {
        log.info("add delay task:{},delay time:{}s", task, seconds);
        delayedQueue.offer(task, seconds, TimeUnit.SECONDS);
    }


}
