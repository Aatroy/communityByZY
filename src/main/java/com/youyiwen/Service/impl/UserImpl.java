package com.youyiwen.Service.impl;

import com.youyiwen.Bean.User;
import com.youyiwen.Mapper.UserMapper;
import com.youyiwen.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhaoyang
 * @Date: 2021/03/04
 */

@Service
public class UserImpl implements UserService {

    @Autowired
    UserMapper mapper;

    @Override
    public User searchUserByName(String name) {
        return mapper.searchUserByName(name);
    }

    @Override
    public void insertUser(User user) {
        mapper.insertUser(user);
    }

    @Override
    public String searchPasswordByName(String name) {
        return mapper.searchPasswordByName(name);
    }

    @Override
    public String searchRole(User user) {
        String userName =user.getUserName();
        return mapper.searchRole(userName);
    }

}
