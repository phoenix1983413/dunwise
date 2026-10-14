package com.dunwise.starter.mybatis;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.dunwise.mica.core.constant.CommonConstants;
import com.dunwise.mica.core.context.ContextHolder;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

/**
 * 多租户行处理器
 */
public class DunwiseTenantLineHandler implements TenantLineHandler {

    @Override
    public Expression getTenantId() {
        String tenantId = ContextHolder.getTenantId();
        if (tenantId == null) {
            tenantId = CommonConstants.DEFAULT_TENANT_ID;
        }
        return new StringValue(tenantId);
    }

    @Override
    public String getTenantIdColumn() {
        return CommonConstants.TENANT_ID;
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return false;
    }

    @Override
    public boolean ignoreInsert(String sql, String tableName) {
        return false;
    }

    @Override
    public boolean ignoreUpdate(String sql, String tableName) {
        return false;
    }

    @Override
    public boolean ignoreDelete(String sql, String tableName) {
        return false;
    }
}
