package com.example.repository.internal;

import com.example.common.base.common.Page;
import com.example.common.entity.RoleEntity;
import com.example.common.filter.RoleFilter;
import com.example.repository.api.RoleDao;
import com.example.repository.mapper.RoleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao{

    private final RoleMapper mapper;

    public RoleDaoImpl(RoleMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int insert(RoleEntity entity) {
        return 0;
    }

    @Override
    public int update(RoleEntity entity) {
        return 0;
    }

    @Override
    public int delete(RoleFilter filter) {
        return 0;
    }

    @Override
    public Page<RoleEntity> findAllWithPagination(RoleFilter filter) {
        List<RoleEntity> data = mapper.findAll(filter);
        int total = mapper.countAll(filter);
        return new Page<>(total, filter.getPageSize(), filter.getPageNumber(), data);
    }

    @Override
    public List<RoleEntity> findAll(RoleFilter filter) {
        return mapper.findAll(filter);
    }

    @Override
    public Optional<RoleEntity> findOne(RoleFilter filter) {
        return mapper.findAll(filter).stream().findFirst();
    }

    @Override
    public Optional<RoleEntity> findById(String id) {
        return Optional.ofNullable(mapper.findById(id));
    }
}
