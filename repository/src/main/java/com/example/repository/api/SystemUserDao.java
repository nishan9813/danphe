package com.example.repository.api;

import com.example.common.base.AbstractDao;
import com.example.common.domain.SystemUserDomain;
import com.example.common.entity.SystemUserEntity;
import com.example.common.filter.SystemUserFilter;

import java.util.Optional;

public interface SystemUserDao extends AbstractDao< SystemUserFilter, SystemUserEntity> {
    Optional<SystemUserDomain> findByEmail(String Email);
}
