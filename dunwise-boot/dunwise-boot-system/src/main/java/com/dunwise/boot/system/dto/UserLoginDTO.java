package com.dunwise.boot.system.dto;

import lombok.Data;

/**
 * 用户登录请求
 */
@Data
public class UserLoginDTO {

    private String username;

    private String password;
}
