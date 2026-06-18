package com.example.common.entity;

import lombok.Data;

@Data
public class SystemUserEntity extends AbstractEntity {
    private String fullName;
    private String email;
    private String password;
    private String roleId;
}
