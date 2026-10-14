package com.dunwise.starter.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.dunwise.mica.core.context.ContextHolder;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis-Plus 自动配置
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(MybatisProperties.class)
public class DunwiseMybatisAutoConfiguration {

    private final MybatisProperties properties;

    public DunwiseMybatisAutoConfiguration(MybatisProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        if (properties.isPaginationEnabled()) {
            PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
            paginationInterceptor.setDbType(DbType.POSTGRESQL);
            paginationInterceptor.setMaxLimit(properties.getMaxLimit());
            paginationInterceptor.setOverflow(false);
            interceptor.addInnerInterceptor(paginationInterceptor);
        }

        if (properties.isTenantEnabled()) {
            TenantLineHandler tenantLineHandler = new DunwiseTenantLineHandler();
            com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor tenantInterceptor =
                    new com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor(tenantLineHandler);
            interceptor.addInnerInterceptor(tenantInterceptor);
        }

        return interceptor;
    }
}
