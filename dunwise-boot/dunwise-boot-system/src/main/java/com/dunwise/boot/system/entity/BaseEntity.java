package com.dunwise.boot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dunwise.mica.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 基础实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT)
    private String tenantId = CommonConstants.DEFAULT_TENANT_ID;

    @TableLogic
    private Boolean deleted = false;

    @Version
    private Integer version = 1;
}
