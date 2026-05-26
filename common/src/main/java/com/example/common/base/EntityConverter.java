package com.example.common.base;

import java.util.List;

public interface EntityConverter<D extends Domain, E extends Entity> extends Converter{
    E toEntity(D domain);

    D toDomain(E entity);

    List<D> toDomainList(List<E> entity);
}
