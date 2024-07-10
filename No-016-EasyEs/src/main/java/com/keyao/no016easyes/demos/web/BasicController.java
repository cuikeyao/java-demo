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

package com.keyao.no016easyes.demos.web;

import jakarta.annotation.Resource;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BasicController {

    @Resource
    private ProductsMapper productsMapper;

    @RequestMapping("/get/{id}")
    public Products get(@PathVariable Integer id) {
        LambdaEsQueryWrapper<Products> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(Products::getId, id);
        return productsMapper.selectById(id);
    }

    @RequestMapping("/getDescription/{description}")
    public List<Products> getDescription(@PathVariable String description) {
        // 会对输入做分词,只要所有分词中有一个词在内容中有匹配就会查询出该数据,无视分词顺序
        LambdaEsQueryWrapper<Products> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.match(Products::getDescription, description);
        // 创建排序规则
        SortBuilder sortBuilder = SortBuilders.fieldSort("id").order(SortOrder.ASC);
        wrapper.sort(sortBuilder);

        wrapper.size(100);
        return productsMapper.selectList(wrapper);
    }
}
