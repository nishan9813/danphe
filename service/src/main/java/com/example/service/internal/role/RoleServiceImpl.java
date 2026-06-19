package com.example.service.internal.role;

import com.example.common.base.common.Page;
import com.example.common.converter.RoleConverter;
import com.example.common.domain.RoleDomain;
import com.example.common.filter.RoleFilter;
import com.example.repository.api.RoleDao;
import com.example.service.api.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao dao;
    private final RoleConverter converter;

    public RoleServiceImpl(RoleDao dao, RoleConverter converter) {
        this.dao = dao;
        this.converter = converter;
    }


    @Override
    public RoleDomain insert(RoleDomain req) {
        return null;
    }

    @Override
    public RoleDomain update(RoleDomain req) {
        return null;
    }

    @Override
    public String delete(RoleDomain req) {
        return "";
    }

    @Override
    public Optional<RoleDomain> findById(String id) {
        return dao.findById(id).map(converter::toDomain);
    }

    @Override
    public Optional<RoleDomain> findOne(RoleFilter filter) {
        return Optional.empty();
    }

    @Override
    public List<RoleDomain> findAllWithoutPagination(RoleFilter filter) {
        return dao.findAllWithPagination(filter).map(converter::toDomain).data();
    }

    @Override
    public Page<RoleDomain> findAll(RoleFilter filter) {
        return dao.findAllWithPagination(filter).map(converter::toDomain);
    }

}
