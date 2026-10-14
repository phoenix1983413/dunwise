CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    parent_id BIGINT NOT NULL DEFAULT 0,
    path VARCHAR(256),
    component VARCHAR(256),
    perms VARCHAR(128),
    icon VARCHAR(128),
    type VARCHAR(32),
    sort INTEGER NOT NULL DEFAULT 0,
    status VARCHAR(2) NOT NULL DEFAULT 'Y',
    visible VARCHAR(2) NOT NULL DEFAULT 'Y',
    tenant_id VARCHAR(64) NOT NULL DEFAULT '000000',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(64),
    update_by VARCHAR(64),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_sys_menu_parent ON sys_menu(parent_id);
CREATE INDEX IF NOT EXISTS idx_sys_menu_tenant ON sys_menu(tenant_id);
