package com.example.common.converter;

import com.example.common.base.Domain;
import com.example.common.base.Entity;
import com.example.common.base.EntityConverter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class AbstractConverter<D extends Domain, E extends Entity> implements EntityConverter<D, E> {
    @Override
    public E toEntity(D domain) {
        if (domain != null) {
            E entity = this.toEntityBase(domain);
            entity.setId(domain.getId());
            entity.setRemark(domain.getRemark());
            entity.setVersion(domain.getVersion());
            entity.setActive(domain.isActive());
            entity.setDeleted(domain.isDeleted());
            entity.setCreatedBy(domain.getCreatedBy());
            entity.setCreatedOn(domain.getCreatedOn());
            entity.setLastModifiedBy(domain.getLastModifiedBy());
            entity.setLastModifiedOn(domain.getLastModifiedOn());
            entity.setDeletedBy(domain.getDeletedBy());
            entity.setDeletedOn(domain.getDeletedOn());
            return entity;
        }
        return null;
    }

    @Override
    public D toDomain(E entity) {
        if (entity != null) {
            D domain = this.toDomainBase(entity);
            domain.setRemark(entity.getRemark());
            domain.setVersion(entity.getVersion());
            domain.setCreatedBy(entity.getCreatedBy());
            domain.setCreatedOn(entity.getCreatedOn());
            domain.setLastModifiedBy(entity.getLastModifiedBy());
            domain.setLastModifiedOn(entity.getLastModifiedOn());
            domain.setDeletedBy(entity.getDeletedBy());
            domain.setDeletedOn(entity.getDeletedOn());
            domain.setActive(entity.isActive());
            domain.setDeleted(entity.isDeleted());
            domain.setId(entity.getId());
            return domain;
        }
        return null;
    }

    @Override
    public List<D> toDomainList(List<E> entities) {
        List<D> domains = new ArrayList<>();
        entities.forEach(e -> domains.add(toDomain(e)));
        return domains;
    }

    protected void copyProperties(Object from, Object to) {
        BeanUtils.copyProperties(from, to);
    }

    @SuppressWarnings("unchecked")
    private Class<E> getEntityClass() {
        return (Class<E>)
                ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[1];
    }

    @SuppressWarnings("unchecked")
    private Class<D> getDomainClass() {
        return (Class<D>)
                ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    protected E toEntityBase(D domain) {
        try {
            E entity = (E) getEntityClass().getDeclaredConstructor().newInstance();
            copyProperties(domain, entity);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create entity instance", e);
        }
    }

    protected D toDomainBase(E entity) {
        try {
            D domain = (D) getDomainClass().getDeclaredConstructor().newInstance();
            copyProperties(entity, domain);
            return domain;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create domain instance", e);
        }
    }
}
