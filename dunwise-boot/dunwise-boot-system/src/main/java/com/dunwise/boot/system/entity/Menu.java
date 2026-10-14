package com.dunwise.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class Menu extends BaseEntity {

    @TableField("name")
    private String name;

    @TableField("parent_id")
    private Long parentId;

    @TableField("path")
    private String path;

    @TableField("component")
    private String component;

    @TableField("perms")
    private String perms;

    @TableField("icon")
    private String icon;

    @TableField("type")
    private String type;

    @TableField("sort")
    private Integer sort;

    @TableField("status")
    private String status = CommonConstants.YES;

    @TableField("visible")
    private String visible = CommonConstants.YES;
}
