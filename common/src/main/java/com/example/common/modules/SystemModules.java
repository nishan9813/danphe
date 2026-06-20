package com.example.common.modules;

import com.example.common.domain.helper.ModuleInfo;
import com.example.common.domain.helper.PermissionInfo;
import com.example.common.domain.helper.Permissions;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public enum SystemModules {
    SYSTEM_USER("System User"),
    ROLE("Role Management"),
    CLIENT_USER("Client User"),
    CHECKER_REQUEST("Checker Request") {
        @Override
        public List<PermissionInfo> getPermissions() {
            return List.of(
                    makePerm("CHECKER_REQUEST_READ", "Read Checker Request"),
                    makePerm("CHECKER_REQUEST_REVIEW", "Review Checker Request"));
        }
    },
    ;

    private final String title;

    SystemModules(String title) {
        this.title = title;
    }

    public static List<Permissions> allModulePermissions() {
        return Arrays.stream(values()).map(SystemModules::toPermissions).collect(Collectors.toList());
    }

    static PermissionInfo makePerm(String code, String title) {
        PermissionInfo p = new PermissionInfo();
        p.setCode(code);
        p.setTitle(title);
        return p;
    }

    public String getEntity() {
        return name();
    }

    public int getOrder() {
        return ordinal();
    }

    public List<PermissionInfo> getPermissions() {
        return List.of(
                perm("_READ", "Read "),
                perm("_CREATE", "Create "),
                perm("_UPDATE", "Update "),
                perm("_DELETE", "Delete "));
    }

    public PermissionInfo readPermission() {
        return perm("_READ", "Read ");
    }

    public List<PermissionInfo> writePermissions() {
        return List.of(
                perm("_CREATE", "Create "), perm("_UPDATE", "Update "), perm("_DELETE", "Delete "));
    }

    public Permissions toPermissions() {
        ModuleInfo moduleInfo = new ModuleInfo();
        moduleInfo.setCode(name());
        moduleInfo.setTitle(title);

        Permissions p = new Permissions();
        p.setModule(moduleInfo);
        p.setPermissions(getPermissions());
        return p;
    }

    private PermissionInfo perm(String suffix, String actionPrefix) {
        PermissionInfo p = new PermissionInfo();
        p.setCode(getEntity() + suffix);
        p.setTitle(actionPrefix + title);
        return p;
    }
}
