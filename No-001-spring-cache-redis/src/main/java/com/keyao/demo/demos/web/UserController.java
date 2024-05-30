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

package com.keyao.demo.demos.web;

import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
public class UserController {

    @Resource
    UserService userService;

    @Cacheable(value = "userCache", key = "#id")
    @RequestMapping("/getUser")
    public User getUser(Integer id) {
        return userService.getById(id);
    }

    @CacheEvict(value = "userCache", key = "#id")
    @RequestMapping("/deleteUser")
    public boolean deleteUser(Integer id) {
        return userService.removeById(id);
    }
    @CachePut(value = "userCache", key = "#user.id", condition = "#result != null")
    @RequestMapping("/updateUser")
    public User updateUser(User user) {
        if (userService.update(user, null)) {
            return user;
        } else {
            return null;
        }
    }

    @CachePut(value = "userCache", key = "#user.id", condition = "#result != null")
    @RequestMapping("/addUser")
    public User addUser(User user) {
        if(userService.save(user)) {
            return user;
        } else {
            return null;
        }
    }

}
