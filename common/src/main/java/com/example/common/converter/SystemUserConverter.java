package com.example.common.converter;

import com.example.common.domain.SystemUserDomain;
import com.example.common.entity.SystemUserEntity;
import org.springframework.stereotype.Component;

@Component
public class SystemUserConverter extends AbstractConverter<SystemUserDomain, SystemUserEntity> {
    @Override
    public SystemUserEntity toEntity(SystemUserDomain domain) {
        if (domain == null) return null;
        SystemUserEntity entity = new SystemUserEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setFullName(domain.getFullName());
        entity.setRoleId(domain.getRoleId());
        return entity;
    }

    @Override
    public SystemUserDomain toDomain(SystemUserEntity entity) {
        if (entity == null) return null;
        SystemUserDomain domain = new SystemUserDomain();
        domain.setId(entity.getId());
        domain.setEmail(entity.getEmail());
        domain.setPassword(entity.getPassword());
        domain.setFullName(entity.getFullName());
        domain.setRoleId(entity.getRoleId());
        return domain;
    }
}
