package com.keyao.no010dynamicdatasource.demos.web;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserService {

    Boolean add();

    List<User> select1();

    List<User> select2();
}
