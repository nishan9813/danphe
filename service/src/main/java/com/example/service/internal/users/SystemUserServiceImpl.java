package com.example.service.internal.users;

import com.example.common.base.common.Page;
import com.example.common.converter.RoleConverter;
import com.example.common.converter.SystemUserConverter;
import com.example.common.domain.SystemUserDomain;
import com.example.common.filter.SystemUserFilter;
import com.example.repository.api.AuditDao;
import com.example.repository.api.RoleDao;
import com.example.repository.api.SystemUserDao;
import com.example.service.api.SystemUserService;
import com.example.service.internal.usecase.systemuser.SystemUserAddUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SystemUserServiceImpl implements SystemUserService {


    private final SystemUserDao repo;
    private final SystemUserConverter converter;
    private final AuditDao auditDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;
    private final RoleConverter roleConverter;

    public SystemUserServiceImpl(SystemUserDao repo, SystemUserConverter converter, AuditDao auditDao, PasswordEncoder passwordEncoder, RoleDao roleDao, RoleConverter roleConverter) {
        this.repo = repo;
        this.converter = converter;
        this.auditDao = auditDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
        this.roleConverter = roleConverter;
    }


    @Override
    public SystemUserDomain create(SystemUserDomain domain) {
        domain.setPassword(passwordEncoder.encode(domain.getPassword()));
        SystemUserAddUseCase systemUserAddUseCase =
                new SystemUserAddUseCase(this.converter, this.auditDao, this.repo);
        systemUserAddUseCase.execute(domain);
        return domain;
    }

    @Override
    public Optional<SystemUserDomain> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public SystemUserDomain insert(SystemUserDomain req) {
        return null;
    }

    @Override
    public SystemUserDomain update(SystemUserDomain req) {
        return null;
    }

    @Override
    public String delete(SystemUserDomain req) {
        return "";
    }

    @Override
    public Optional<SystemUserDomain> findById(String id) {
        Optional<SystemUserDomain> result = repo.findById(id).map(converter::toDomain);
        result.ifPresent(this::enrichWithRole);
        return result;
    }

    @Override
    public Optional<SystemUserDomain> findOne(SystemUserFilter filter) {
        return Optional.empty();
    }

    @Override
    public List<SystemUserDomain> findAllWithoutPagination(SystemUserFilter filter) {
        return List.of();
    }

    @Override
    public Page<SystemUserDomain> findAll(SystemUserFilter filter) {
        return repo.findAllWithPagination(filter).map(converter::toDomain);
    }

    private void enrichWithRole(SystemUserDomain domain) {
        if (domain != null && domain.getRoleId() != null) {
            roleDao.findById(domain.getRoleId()).map(roleConverter::toDomain).ifPresent(domain::setRoleDomain);
        }
    }
}
