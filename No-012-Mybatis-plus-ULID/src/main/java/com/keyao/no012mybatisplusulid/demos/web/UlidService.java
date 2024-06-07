package com.keyao.no012mybatisplusulid.demos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UlidService {
    @Autowired
    private UlidMapper ulidMapper;

    @Transactional
    public Boolean add(Ulid ulid) {
        return ulidMapper.insert(ulid) > 0;
    }

    public List<Ulid> getList() {
        return ulidMapper.selectList(null);
    }
}
