package com.example.common.entity;

import lombok.Data;

@Data
public class SystemUserEntity extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
