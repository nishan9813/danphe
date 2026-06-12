package com.example.common.entity;

import lombok.Data;

import java.security.Permission;
import java.util.List;

@Data
public class RoleEntity extends AbstractEntity {
    private String title;
    private List<Permission> permission;
}
