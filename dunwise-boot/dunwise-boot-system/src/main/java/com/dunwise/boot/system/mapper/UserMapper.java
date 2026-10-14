package com.dunwise.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dunwise.boot.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findByUsername(@Param("username") String username);

    List<User> selectUserRoleInfo(@Param("userId") Long userId);
}
