package com.example.common.response;

import com.example.common.domain.RoleDomain;
import com.example.common.domain.SystemUserDomain;

public record SystemUserResponse(
        String id,
        String fullName,
        String email,
        String roleId,
        RoleDomain roleDomain
) {
    public static SystemUserResponse from(SystemUserDomain domain){
        if (domain == null) return null;
        return new SystemUserResponse(
                domain.getId(),
                domain.getFullName(),
                domain.getEmail(),
                domain.getRoleId(),
                domain.getRoleDomain());
    }
}
