package com.dunwise.starter.mybatis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

/**
 * MyBatis-Plus 配置属性
 */
@ConfigurationProperties(prefix = "dunwise.mybatis")
public class MybatisProperties {

    private boolean paginationEnabled = true;

    private boolean tenantEnabled = true;

    private long maxLimit = 500L;

    public MybatisProperties() {
    }

    public boolean isPaginationEnabled() {
        return paginationEnabled;
    }

    public void setPaginationEnabled(boolean paginationEnabled) {
        this.paginationEnabled = paginationEnabled;
    }

    public boolean isTenantEnabled() {
        return tenantEnabled;
    }

    public void setTenantEnabled(boolean tenantEnabled) {
        this.tenantEnabled = tenantEnabled;
    }

    public long getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(long maxLimit) {
        this.maxLimit = maxLimit;
    }
}
