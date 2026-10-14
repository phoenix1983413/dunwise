package com.dunwise.boot.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dunwise.boot.system.entity.Role;
import com.dunwise.boot.system.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * 角色服务实现
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
