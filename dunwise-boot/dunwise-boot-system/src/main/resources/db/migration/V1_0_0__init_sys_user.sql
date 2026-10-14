CREATE TABLE IF NOT EXISTS sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    nickname VARCHAR(128),
    email VARCHAR(128),
    phone VARCHAR(32),
    password VARCHAR(128) NOT NULL,
    avatar VARCHAR(512),
    sex VARCHAR(2) DEFAULT '0',
    status VARCHAR(2) DEFAULT 'Y',
    last_login_time TIMESTAMP,
    last_login_ip VARCHAR(64),
    tenant_id VARCHAR(64) NOT NULL DEFAULT '000000',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(64),
    update_by VARCHAR(64),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_sys_user_username ON sys_user(username);
CREATE INDEX IF NOT EXISTS idx_sys_user_tenant ON sys_user(tenant_id);
