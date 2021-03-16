package com.youyiwen.Bean;

import lombok.Data;

/**
 * @Author: zhaoyang
 * @Date: 2021/03/15
 */
@Data
public class AccessToken {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
