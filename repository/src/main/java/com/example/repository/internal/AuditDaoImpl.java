package com.example.repository.internal;

import com.example.common.base.audits.AuditEntity;
import com.example.common.base.audits.AuditFilter;
import com.example.common.base.common.Page;
import com.example.common.converter.AuditConvertor;
import com.example.repository.api.AuditDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuditDaoImpl implements AuditDao {
    private final AuditConvertor convertor;

    public AuditDaoImpl(AuditConvertor convertor) {
        this.convertor = convertor;
    }


    @Override
    public int insert(AuditEntity entity) {
        return 0;
    }

    @Override
    public int update(AuditEntity entity) {
        return 0;
    }

    @Override
    public int delete(AuditFilter filter) {
        return 0;
    }

    @Override
    public Page<AuditEntity> findAllWithPagination(AuditFilter filter) {
        return null;
    }

    @Override
    public List<AuditEntity> findAll(AuditFilter filter) {
        return List.of();
    }

    @Override
    public Optional<AuditEntity> findOne(AuditFilter filter) {
        return Optional.empty();
    }

    @Override
    public Optional<AuditEntity> findById(String id) {
        return Optional.empty();
    }
}
