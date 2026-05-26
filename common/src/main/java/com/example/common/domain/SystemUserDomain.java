package com.example.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SystemUserDomain extends AbstractDomain {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String roleId;

    @JsonIgnore
    private String roleDomain;
}
