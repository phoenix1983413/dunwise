package com.dunwise.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dunwise.boot.system.entity.DictData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典数据 Mapper
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {
}
