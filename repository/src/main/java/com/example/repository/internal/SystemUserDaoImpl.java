package com.example.repository.internal;

import com.example.common.base.Converter;
import com.example.common.base.common.Page;
import com.example.common.converter.SystemUserConverter;
import com.example.common.domain.SystemUserDomain;
import com.example.common.entity.SystemUserEntity;
import com.example.common.filter.SystemUserFilter;
import com.example.repository.api.SystemUserDao;
import com.example.repository.mapper.SystemUserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SystemUserDaoImpl implements SystemUserDao {

    private final SystemUserMapper mapper;
    private final SystemUserConverter converter;

    public SystemUserDaoImpl(SystemUserMapper mapper, SystemUserConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }


    @Override
    public int insert(SystemUserEntity entity) {
        return mapper.insert(entity);
    }

    @Override
    public int update(SystemUserEntity entity) {
        return 0;
    }

    @Override
    public int delete(SystemUserFilter filter) {
        return 0;
    }

    @Override
    public Page<SystemUserEntity> findAllWithPagination(SystemUserFilter filter) {
        return null;
    }

    @Override
    public List<SystemUserEntity> findAll(SystemUserFilter filter) {
        return List.of();
    }

    @Override
    public Optional<SystemUserEntity> findOne(SystemUserFilter filter) {
        return Optional.empty();
    }

    @Override
    public Optional<SystemUserEntity> findById(String id) {
        return Optional.ofNullable(mapper.findById(id));
    }

    @Override
    public Optional<SystemUserDomain> findByEmail(String email) {
        Optional<SystemUserDomain> domain = Optional.ofNullable(converter.toDomain(mapper.findByEmail(email)));
        return domain;
    }
}
