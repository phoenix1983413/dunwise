package com.dunwise.boot.system.dto;

import lombok.Data;

/**
 * 用户注册请求
 */
@Data
public class UserRegisterDTO {

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;
}
