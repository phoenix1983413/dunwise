package com.dunwise.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
public class DictType extends BaseEntity {

    @TableField("name")
    private String name;

    @TableField("type")
    private String type;

    @TableField("status")
    private String status = CommonConstants.YES;

    @TableField("remark")
    private String remark;
}
