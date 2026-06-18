package com.example.service.internal.users;

import com.example.common.base.common.Page;
import com.example.common.converter.SystemUserConverter;
import com.example.common.domain.SystemUserDomain;
import com.example.common.filter.SystemUserFilter;
import com.example.repository.api.AuditDao;
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

    public SystemUserServiceImpl(SystemUserDao repo, SystemUserConverter converter, AuditDao auditDao, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.converter = converter;
        this.auditDao = auditDao;
        this.passwordEncoder = passwordEncoder;
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
        return repo.findById(id).map(converter::toDomain);
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
        return null;
    }
}
