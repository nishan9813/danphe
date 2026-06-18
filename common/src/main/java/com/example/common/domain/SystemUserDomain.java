package com.example.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SystemUserDomain extends AbstractDomain {
    private String fullName;
    private String email;
    private String roleId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    private RoleDomain roleDomain;
}
