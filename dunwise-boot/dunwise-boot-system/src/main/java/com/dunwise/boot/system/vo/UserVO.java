package com.dunwise.boot.system.vo;

import com.dunwise.boot.system.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息响应
 */
@Data
public class UserVO {

    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private String avatar;

    private String status;

    private LocalDateTime lastLoginTime;

    private List<Role> roles;
}
