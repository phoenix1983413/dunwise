package com.dunwise.mica.core.context;

/**
 * 上下文持有器
 */
public class ContextHolder {

    private static final ThreadLocal<Context> CONTEXT_HOLDER = new ThreadLocal<>();

    public static Context getContext() {
        Context context = CONTEXT_HOLDER.get();
        if (context == null) {
            context = new Context();
            CONTEXT_HOLDER.set(context);
        }
        return context;
    }

    public static void setContext(Context context) {
        if (context == null) {
            removeContext();
            return;
        }
        CONTEXT_HOLDER.set(context);
    }

    public static void removeContext() {
        CONTEXT_HOLDER.remove();
    }

    public static String getTenantId() {
        Context context = CONTEXT_HOLDER.get();
        return context != null ? context.getTenantId() : null;
    }

    public static void setTenantId(String tenantId) {
        getContext().setTenantId(tenantId);
    }

    public static String getUserId() {
        Context context = CONTEXT_HOLDER.get();
        return context != null ? context.getUserId() : null;
    }

    public static void setUserId(String userId) {
        getContext().setUserId(userId);
    }

    public static String getUsername() {
        Context context = CONTEXT_HOLDER.get();
        return context != null ? context.getUsername() : null;
    }

    public static void setUsername(String username) {
        getContext().setUsername(username);
    }

    public static String getTraceId() {
        Context context = CONTEXT_HOLDER.get();
        return context != null ? context.getTraceId() : null;
    }

    public static void setTraceId(String traceId) {
        getContext().setTraceId(traceId);
    }

    public static boolean isSuperAdmin() {
        Context context = CONTEXT_HOLDER.get();
        return context != null && context.isSuperAdmin();
    }
}
