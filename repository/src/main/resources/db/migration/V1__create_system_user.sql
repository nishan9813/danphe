
CREATE SCHEMA IF NOT EXISTS system;

CREATE TABLE system.system_users
(
    id               VARCHAR(36)  PRIMARY KEY,
    password         VARCHAR(255) NOT NULL,
    email            VARCHAR(255) NOT NULL UNIQUE,
    full_name        VARCHAR(255),
    role_id          VARCHAR(36),
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