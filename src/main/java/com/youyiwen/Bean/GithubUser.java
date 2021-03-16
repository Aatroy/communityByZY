package com.youyiwen.Bean;

import lombok.Data;

/**
 * @Author: zhaoyang
 * @Date: 2021/03/15
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    public String getUserName(){
        return name;
    }
}
