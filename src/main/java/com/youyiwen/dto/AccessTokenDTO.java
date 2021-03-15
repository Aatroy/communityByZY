package com.youyiwen.dto;

import lombok.Data;

/**
 * @Author: zhaoyang
 * @Date: 2021/03/15
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
