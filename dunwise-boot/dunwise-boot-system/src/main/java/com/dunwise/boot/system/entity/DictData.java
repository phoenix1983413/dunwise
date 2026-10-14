package com.dunwise.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
public class DictData extends BaseEntity {

    @TableField("dict_type_id")
    private Long dictTypeId;

    @TableField("label")
    private String label;

    @TableField("value")
    private String value;

    @TableField("sort")
    private Integer sort;

    @TableField("status")
    private String status = CommonConstants.YES;

    @TableField("remark")
    private String remark;
}
