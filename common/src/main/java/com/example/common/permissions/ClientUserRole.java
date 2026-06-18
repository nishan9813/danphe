package com.example.common.permissions;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum ClientUserRole {
    CLIENT_USER(Set.of(ClientUserPermission.values())),

    GUEST(EnumSet.of(
            ClientUserPermission.CLIENT_USER_READ
    ));

    private final Set<ClientUserPermission> permissions;


    ClientUserRole(Set<ClientUserPermission> permissions) {
        this.permissions = Collections.unmodifiableSet(permissions);
    }
}
