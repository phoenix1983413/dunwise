CREATE TABLE IF NOT EXISTS sys_dict_type (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    type VARCHAR(128) NOT NULL UNIQUE,
    status VARCHAR(2) NOT NULL DEFAULT 'Y',
    remark VARCHAR(512),
    tenant_id VARCHAR(64) NOT NULL DEFAULT '000000',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(64),
    update_by VARCHAR(64),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_sys_dict_type_type ON sys_dict_type(type);
CREATE INDEX IF NOT EXISTS idx_sys_dict_type_tenant ON sys_dict_type(tenant_id);
