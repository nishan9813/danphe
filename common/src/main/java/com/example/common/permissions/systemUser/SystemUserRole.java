package com.example.common.permissions.systemUser;

import com.example.common.permissions.ClientUserPermission;
import lombok.Getter;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@Getter
public enum SystemUserRole {

    SUPER_ADMIN(
            Set.of(SystemUserPermissions.values()),
            Set.of(ClientUserPermission.values())),

    ADMIN(
            EnumSet.of(SystemUserPermissions.SYSTEM_USER_READ),
            EnumSet.of(ClientUserPermission.CLIENT_USER_READ));

    private final Set<SystemUserPermissions> permissions;
    private final Set<ClientUserPermission> clientUserPermissions;

    SystemUserRole(Set<SystemUserPermissions> permissions, Set<ClientUserPermission> clientUserPermissions) {
        this.permissions = Collections.unmodifiableSet(permissions);
        this.clientUserPermissions = Collections.unmodifiableSet(clientUserPermissions);
    }
}
