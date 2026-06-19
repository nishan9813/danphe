package com.example.common.domain;

import com.example.common.permissions.ClientUserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientUserDomain extends AbstractDomain{

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;
    private ClientUserRole role;
    private boolean verified;
//    private AuthFlow authFlow;

    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate dateOfBirth;
    private String profilePicture;
}
