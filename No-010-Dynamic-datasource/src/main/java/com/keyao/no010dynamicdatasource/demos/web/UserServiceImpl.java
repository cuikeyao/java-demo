package com.keyao.no010dynamicdatasource.demos.web;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @DSTransactional(rollbackFor = Exception.class)
    @DS("master")
    @Override
    public Boolean add() {
        User user = new User();
        user.setName("No-010-" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        user.setAge(18);
        return userMapper.insert(user) > 0;
    }

    @DS("slave")
    @Override
    public List<User> select1() {
        return userMapper.selectList(null);
    }

    @DS("slave")
    @Override
    public List<User> select2() {
        return userMapper.selectList(null);
    }
}
