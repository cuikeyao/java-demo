package com.keyao.no004completablefutureutil.demos.web;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public Long countFans() {
        ThreadUtil.sleep(5);
        return 10L;
    }

    public Long countRedBag() {
        ThreadUtil.sleep(2);
        return 20L;
    }

    public Long countMsg() {
        ThreadUtil.sleep(1);
        return 5L;
    }
}

