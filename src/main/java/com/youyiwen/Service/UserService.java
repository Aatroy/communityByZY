package com.youyiwen.Service;

import com.youyiwen.Bean.User;

/**
 * @Author: zhaoyang
 * @Date: 2021/03/04
 */
public interface UserService {
    public User searchUserByName(String name);
    public void insertUser(User user);
    public String searchPasswordByName(String name);
    public String searchRole(User user);
}
