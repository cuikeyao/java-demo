/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.keyao.no003completablefuture.demos.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
public class BasicController {
    private static final Logger log = LogManager.getLogger(BasicController.class);
    @Autowired
    UserService userService;

    private static ExecutorService executor = new ThreadPoolExecutor(20, 50, 30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

    // http://192.168.56.10:9003/get
    @RequestMapping("/get")
    public Map get() {
        Map<String,Long> map = new HashMap<>();
        try {
            CompletableFuture<Long> countFans = CompletableFuture.supplyAsync(
                    () -> userService.countFans(), executor);

            CompletableFuture<Long> countRedBag = CompletableFuture.supplyAsync(
                    () -> userService.countRedBag(), executor);

            CompletableFuture<Long> countMsg = CompletableFuture.supplyAsync(
                    () -> userService.countMsg(), executor);

            CompletableFuture.allOf(countFans, countRedBag, countMsg).get(10, TimeUnit.SECONDS);

            map.putIfAbsent("countFans:", countFans.get());
            map.putIfAbsent("countRedBag:", countRedBag.get());
            map.putIfAbsent("countMsg:", countMsg.get());
        } catch (ExecutionException | InterruptedException | TimeoutException ex) {
            log.error("Exception", ex);
        }
        return map;
    }
}
