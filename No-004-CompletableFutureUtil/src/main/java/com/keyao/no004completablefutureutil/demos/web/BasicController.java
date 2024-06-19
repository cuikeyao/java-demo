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

package com.keyao.no004completablefutureutil.demos.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class BasicController {
    private static final Logger log = LogManager.getLogger(BasicController.class);
    @Autowired
    UserService userService;

    @RequestMapping("/get")
    public List get() {
        User user1 = User.builder().id(String.valueOf(1L)).name("xiaoming").age(18).build();
        User user2 = User.builder().id(String.valueOf(2L)).name("xiaohong").age(20).build();
        User user3 = User.builder().id(String.valueOf(3L)).name("xiaohuang").age(22).build();

        CompletableFutureUtil completableFutureUtil = new CompletableFutureUtil();
        List<String> strList = completableFutureUtil.parallelFutureJoin(Arrays.asList(user1, user2, user3), item -> {
            ThreadUtil.sleep(5);  //模拟耗时操作
            return item.getName();
        }, (e, item) -> {
            log.error("发生异常，刚才在处理：{}，异常原因：{}", item, e);
            return null;
        });
        return strList;
    }
}
