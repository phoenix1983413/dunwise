package com.dunwise.starter.security.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    String value() default "";

    String tenantAlias() default "t.tenant_id";

    String userAlias() default "t.user_id";

    boolean tenantFilter() default true;

    boolean userFilter() default false;
}
