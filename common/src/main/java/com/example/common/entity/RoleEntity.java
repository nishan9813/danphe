package com.example.common.entity;

import lombok.Data;

@Data
public class RoleEntity extends AbstractEntity {
    private String title;
    private String permissions;
}
