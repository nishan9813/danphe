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
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        return entity;
    }

    @Override
    public SystemUserDomain toDomain(SystemUserEntity entity) {
        if (entity == null) return null;
        SystemUserDomain domain = new SystemUserDomain();
        domain.setEmail(entity.getEmail());
        domain.setPassword(entity.getPassword());
        domain.setFirstName(entity.getFirstName());
        domain.setLastName(entity.getLastName());
        return null;
    }
}
