package com.dunwise.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dunwise.boot.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色 Mapper
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
