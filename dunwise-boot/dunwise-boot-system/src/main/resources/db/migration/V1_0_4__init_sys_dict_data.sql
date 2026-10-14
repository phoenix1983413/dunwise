CREATE TABLE IF NOT EXISTS sys_dict_data (
    id BIGSERIAL PRIMARY KEY,
    dict_type_id BIGINT NOT NULL,
    label VARCHAR(128) NOT NULL,
    value VARCHAR(128) NOT NULL,
    sort INTEGER NOT NULL DEFAULT 0,
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

CREATE INDEX IF NOT EXISTS idx_sys_dict_data_type ON sys_dict_data(dict_type_id);
CREATE INDEX IF NOT EXISTS idx_sys_dict_data_tenant ON sys_dict_data(tenant_id);
