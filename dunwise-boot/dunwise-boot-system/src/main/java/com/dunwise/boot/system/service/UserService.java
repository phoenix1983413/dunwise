package com.dunwise.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dunwise.boot.system.entity.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    User findByUsername(String username);
}
