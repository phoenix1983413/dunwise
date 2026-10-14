package com.dunwise.mica.core.context;

import com.dunwise.mica.core.constant.CommonConstants;
import java.io.Serial;
import java.io.Serializable;

/**
 * 请求上下文
 */
public class Context implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String tenantId;

    private String userId;

    private String username;

    private String traceId;

    private String requestId;

    public Context() {
        this.tenantId = CommonConstants.DEFAULT_TENANT_ID;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isSuperAdmin() {
        return CommonConstants.SUPER_ADMIN.equals(this.username);
    }

    public String getTenantIdOrNull() {
        return CommonConstants.DEFAULT_TENANT_ID.equals(tenantId) ? null : tenantId;
    }
}
