package com.example.service.api;

import com.example.common.base.Filter;
import com.example.common.base.common.Page;

import java.util.List;
import java.util.Optional;

public interface Service<D, F extends Filter> {
    D insert(D req);

    D update(D req);

    String delete(D req);

    Optional<D> findById(String id);

    Optional<D> findOne(F filter);

    List<D> findAllWithoutPagination(F filter);

    Page<D> findAll(F filter);
}
