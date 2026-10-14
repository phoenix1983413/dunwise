package com.dunwise.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dunwise.boot.system.entity.OperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志 Mapper
 */
@Mapper
public interface OperLogMapper extends BaseMapper<OperLog> {
}
