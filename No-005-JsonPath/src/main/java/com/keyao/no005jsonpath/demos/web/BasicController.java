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

package com.keyao.no005jsonpath.demos.web;

import com.alibaba.fastjson2.JSONPath;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
public class BasicController {

    private static final Logger log = LogManager.getLogger(BasicController.class);

    @RequestMapping("/get")
    public String get() {
        String jsonStr = """
                {
                  "store": {
                    "book": [
                      {
                        "category": "reference",
                        "author": "Nigel Rees",
                        "title": "Sayings of the Century",
                        "price": 8.95
                      },
                      {
                        "category": "fiction",
                        "author": "Evelyn Waugh",
                        "title": "Sword of Honour",
                        "price": 12.99
                      }
                    ],
                    "bicycle": {
                      "color": "red",
                      "price": 19.95
                    }
                  }
                }""";
        ReadContext ctx = JsonPath.parse(jsonStr);
        log.info("Book数目：" + ctx.read("$.store.book.length()"));
        log.info("bicycle的所有属性值" + ctx.read("$.store.bicycle.*"));
        log.info("bicycle的color和price属性值" + ctx.read("$.store.bicycle['color','price']"));
        log.info("price大于10元的book：" + ctx.read("$.store.book[?(@.price > 10)]"));
        return ctx.read("$").toString();
    }
}
