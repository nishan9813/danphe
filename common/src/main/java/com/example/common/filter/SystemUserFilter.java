package com.example.common.filter;

import lombok.Data;

@Data
public class SystemUserFilter extends AbstractFilter{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
