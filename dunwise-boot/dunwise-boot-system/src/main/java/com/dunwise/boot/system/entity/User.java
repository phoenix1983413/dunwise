package com.dunwise.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dunwise.boot.system.enums.SexEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class User extends BaseEntity {

    @TableField("username")
    private String username;

    @TableField("nickname")
    private String nickname;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("password")
    private String password;

    @TableField("avatar")
    private String avatar;

    @TableField("sex")
    private SexEnum sex;

    @TableField("status")
    private String status = CommonConstants.YES;

    @TableField("last_login_time")
    private java.time.LocalDateTime lastLoginTime;

    @TableField("last_login_ip")
    private String lastLoginIp;
}
