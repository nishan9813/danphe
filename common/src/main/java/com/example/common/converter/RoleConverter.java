package com.example.common.converter;

import com.example.common.domain.RoleDomain;
import com.example.common.domain.helper.Permissions;
import com.example.common.entity.RoleEntity;
import com.example.common.utils.Jsons;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class RoleConverter extends AbstractConverter<RoleDomain, RoleEntity> {

    @Override
    protected RoleEntity toEntityBase(RoleDomain domain) {
        RoleEntity entity = new RoleEntity();
        if (domain.getTitle() != null) {
            entity.setTitle(domain.getTitle());
        }
        if (domain.getPermissions() != null) {
            entity.setPermissions(Jsons.toJsonList(domain.getPermissions()));
        }
        return entity;
    }

    @Override
    protected RoleDomain toDomainBase(RoleEntity entity) {
        RoleDomain domain = new RoleDomain();
        domain.setTitle(entity.getTitle());
        String permissionsJson = entity.getPermissions();
        if (permissionsJson == null || permissionsJson.isEmpty()) {
            domain.setPermissions(new ArrayList<>());
        } else {
            domain.setPermissions(
                    Arrays.asList(Jsons.fromJsonToObj(permissionsJson, Permissions[].class))
            );
        }
        return domain;
    }
}
