package com.example.common.converter;

import com.example.common.base.audits.AuditDomain;
import com.example.common.base.audits.AuditEntity;
import org.springframework.stereotype.Component;

@Component
public class AuditConvertor extends AbstractConverter<AuditDomain, AuditEntity> {

    @Override
    protected AuditEntity toEntityBase(AuditDomain domain) {
        AuditEntity entity = new AuditEntity();
        entity.setEntity(domain.getEntity());
        entity.setEntityKey(domain.getEntityKey());
        entity.setNewEntity(domain.getNewEntity());
        entity.setOldEntity(domain.getOldEntity());
        entity.setRespondedBy(domain.getRespondedBy());
        entity.setRespondedOn(domain.getRespondedOn());
        entity.setRequestedBy(domain.getRequestedBy());
        entity.setRequestedOn(domain.getRequestedOn());
        entity.setRequestType(domain.getRequestType());
        entity.setResource(domain.getResource());

        return entity;
    }
}
