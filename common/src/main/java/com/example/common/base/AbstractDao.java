package com.example.common.base;

import com.example.common.base.common.Page;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<F extends Filter, E extends Entity> extends BaseDao {

    int insert(E entity);

    int update(E entity);

    int delete(E entity);

    Page<E> findAllWithPagination(F filter);

    List<E> findAll(F filter);

    Optional<E> findOne(F filter);

    Optional<E> findById(String id);

}
