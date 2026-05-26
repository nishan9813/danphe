
CREATE SCHEMA IF NOT EXIST system;


CREATE TABLE system.system_user
(
    id         VARCHAR(36)  NOT NULL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL
);