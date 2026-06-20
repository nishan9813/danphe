package com.example.service.api;

import com.example.common.domain.SystemUserDomain;
import com.example.common.filter.SystemUserFilter;

import java.util.Optional;

public interface SystemUserService extends Service<SystemUserDomain, SystemUserFilter> {

    Optional<SystemUserDomain> findByEmail(String email);
}
