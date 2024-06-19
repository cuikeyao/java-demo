package com.keyao.no011mybatisplusdelete.demos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Boolean deleteUserById(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    @Transactional
    public Boolean addUser(User user) {
        return userMapper.insert(user) > 0;
    }

    public List<User> getUserList() {
        return userMapper.selectList(null);
    }
}
