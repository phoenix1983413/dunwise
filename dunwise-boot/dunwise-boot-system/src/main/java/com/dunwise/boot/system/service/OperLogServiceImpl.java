package com.dunwise.boot.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dunwise.boot.system.entity.OperLog;
import com.dunwise.boot.system.mapper.OperLogMapper;
import org.springframework.stereotype.Service;

/**
 * 操作日志服务实现
 */
@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog> implements OperLogService {
}
