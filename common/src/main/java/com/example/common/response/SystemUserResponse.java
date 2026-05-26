package com.example.common.response;

import com.example.common.domain.SystemUserDomain;

public record SystemUserResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String roleId
) {
    public static SystemUserResponse from(SystemUserDomain domain){
        if (domain == null) return null;
        return new SystemUserResponse(
                domain.getId(),
                domain.getFirstName(),
                domain.getLastName(),
                domain.getEmail(),
                domain.getRoleId());
    }
}
