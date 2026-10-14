package com.dunwise.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dunwise.mica.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oper_log")
public class OperLog extends BaseEntity {

    @TableField("title")
    private String title;

    @TableField("business_type")
    private String businessType;

    @TableField("method")
    private String method;

    @TableField("request_method")
    private String requestMethod;

    @TableField("operator_type")
    private String operatorType;

    @TableField("oper_name")
    private String operName;

    @TableField("oper_url")
    private String operUrl;

    @TableField("oper_ip")
    private String operIp;

    @TableField("oper_param")
    private String operParam;

    @TableField("json_result")
    private String jsonResult;

    @TableField("status")
    private String status = CommonConstants.YES;

    @TableField("error_msg")
    private String errorMsg;

    @TableField("cost_time")
    private Long costTime;
}
