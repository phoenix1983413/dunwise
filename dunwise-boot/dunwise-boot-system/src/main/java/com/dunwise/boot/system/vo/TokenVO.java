package com.dunwise.boot.system.vo;

import lombok.Data;

/**
 * 登录令牌响应
 */
@Data
public class TokenVO {

    private String accessToken;

    private String tokenType = "Bearer";

    private Long expiresIn;

    private String username;

    private String userId;
}
