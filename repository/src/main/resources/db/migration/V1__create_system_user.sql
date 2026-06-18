
CREATE SCHEMA IF NOT EXISTS system;


CREATE TABLE system.roles (
                              id               VARCHAR(36)  PRIMARY KEY,
                              title            VARCHAR(255) NOT NULL UNIQUE,
                              permissions      JSONB,
                              active           BOOLEAN      NOT NULL DEFAULT TRUE,
                              created_by       VARCHAR(100),
                              created_on       TIMESTAMP,
                              last_modified_by VARCHAR(100),
                              last_modified_on TIMESTAMP,
                              deleted_by       VARCHAR(100),
                              deleted_on       TIMESTAMP,
                              deleted          BOOLEAN      NOT NULL DEFAULT FALSE,
                              version          BIGINT       DEFAULT 0
);

CREATE TABLE system.system_users
(
    id               VARCHAR(36)  PRIMARY KEY,
    password         VARCHAR(255) NOT NULL,
    email            VARCHAR(255) NOT NULL UNIQUE,
    full_name        VARCHAR(255),
    role_id          VARCHAR(36) REFERENCES system.roles,
    created_on       TIMESTAMP,
    created_by       VARCHAR(100),
    last_modified_on TIMESTAMP,
    last_modified_by VARCHAR(100),
    deleted_on       TIMESTAMP,
    deleted_by       VARCHAR(100),
    active           BOOLEAN      NOT NULL DEFAULT TRUE,
    deleted          BOOLEAN      NOT NULL DEFAULT FALSE,
    remark           TEXT,
    version          BIGINT       DEFAULT 0
);

CREATE TABLE system.audit_logs (
                                   id           VARCHAR(36)  PRIMARY KEY,
                                   entity_key   VARCHAR(255),
                                   request_type VARCHAR(50),
                                   new_data     JSONB,
                                   old_data     JSONB,
                                   requested_by VARCHAR(255),
                                   requested_on TIMESTAMP,
                                   responded_by VARCHAR(255),
                                   responded_on TIMESTAMP,
                                   remarks      TEXT,
                                   entity       VARCHAR(100),
                                   resource     VARCHAR(255)
);

INSERT INTO system.roles (id, title, permissions, active, created_by, created_on, deleted, version)
VALUES (
           'a1b2c3d4-0000-0000-0000-000000000001',
           'SUPER_ADMIN',
           '[
             {"module":{"code":"SYSTEM_USER","title":"System User"},"permissions":[
               {"code":"SYSTEM_USER_CREATE","title":"Create System User"},
               {"code":"SYSTEM_USER_READ","title":"Read System User"},
               {"code":"SYSTEM_USER_UPDATE","title":"Update System User"},
               {"code":"SYSTEM_USER_DELETE","title":"Delete System User"}
             ]},
             {"module":{"code":"ROLE","title":"Role Management"},"permissions":[
               {"code":"ROLE_CREATE","title":"Create Role"},
               {"code":"ROLE_READ","title":"Read Role"},
               {"code":"ROLE_UPDATE","title":"Update Role"},
               {"code":"ROLE_DELETE","title":"Delete Role"}
             ]},
             {"module":{"code":"CLIENT_USER","title":"Client User"},"permissions":[
               {"code":"CLIENT_USER_CREATE","title":"Create Client User"},
               {"code":"CLIENT_USER_READ","title":"Read Client User"},
               {"code":"CLIENT_USER_UPDATE","title":"Update Client User"},
               {"code":"CLIENT_USER_DELETE","title":"Delete Client User"}
             ]}
           ]'::JSONB,
           true,
           'SYSTEM',
           NOW(),
           false,
           0
       )
ON CONFLICT (title) DO NOTHING;


INSERT INTO system.system_users (id, password, email, role_id,
                                created_on, created_by,
                                last_modified_on, last_modified_by,
                                deleted_on, deleted_by,
                                active, deleted, remark, version)
VALUES (
           'e300e50e-144f-4787-aed7-1c8bc2690dd1',
           '$2a$12$UT8i.iyMaO.XJVq5lXazbOuoMGJPLxzQ/JpI4m/fowOR0lMmuqfWy',
           'superadmin@danphe.com',
           'a1b2c3d4-0000-0000-0000-000000000001',
           '2026-04-12 15:15:37.456546', 'system',
           null, null,
           null, null,
           true, false, null, 0
       )
    ON CONFLICT (id) DO NOTHING;