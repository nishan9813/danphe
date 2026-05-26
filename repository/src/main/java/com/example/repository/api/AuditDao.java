package com.example.repository.api;

import com.example.common.base.AbstractDao;
import com.example.common.base.audits.AuditEntity;
import com.example.common.base.audits.AuditFilter;

public interface AuditDao extends AbstractDao<AuditFilter, AuditEntity> {
}
