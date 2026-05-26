package com.example.service.internal.users;

import com.example.common.converter.SystemUserConverter;
import com.example.common.domain.SystemUserDomain;
import com.example.repository.api.AuditDao;
import com.example.repository.api.SystemUserDao;
import com.example.service.api.SystemUserService;
import com.example.service.internal.usecase.systemuser.SystemUserAddUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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
}
