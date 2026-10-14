CREATE TABLE IF NOT EXISTS sys_oper_log (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    business_type VARCHAR(64),
    method VARCHAR(256),
    request_method VARCHAR(32),
    operator_type VARCHAR(32),
    oper_name VARCHAR(128),
    oper_url VARCHAR(512),
    oper_ip VARCHAR(64),
    oper_param TEXT,
    json_result TEXT,
    status VARCHAR(2) NOT NULL DEFAULT 'Y',
    error_msg TEXT,
    cost_time BIGINT,
    tenant_id VARCHAR(64) NOT NULL DEFAULT '000000',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(64),
    update_by VARCHAR(64),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_sys_oper_log_tenant ON sys_oper_log(tenant_id);
CREATE INDEX IF NOT EXISTS idx_sys_oper_log_create_time ON sys_oper_log(create_time);
