package com.example.common.domain.helper;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Permissions {
    private ModuleInfo module;
    private List<PermissionInfo> permissions;
}
