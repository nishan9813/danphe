package com.example.repository.api;

import com.example.common.base.AbstractDao;
import com.example.common.entity.SystemUserEntity;
import com.example.common.filter.SystemUserFilter;

public interface SystemUserDao extends AbstractDao< SystemUserFilter, SystemUserEntity> {
}
