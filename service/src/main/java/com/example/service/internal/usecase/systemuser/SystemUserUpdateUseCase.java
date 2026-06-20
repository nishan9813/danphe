package com.example.service.internal.usecase.systemuser;

import com.example.common.base.AbstractDao;
import com.example.common.base.EntityConverter;
import com.example.common.base.Filter;
import com.example.common.base.abstracts.AbstractUpdateUseCase;
import com.example.common.domain.SystemUserDomain;
import com.example.common.filter.SystemUserFilter;
import com.example.common.modules.SystemModules;

public class SystemUserUpdateUseCase extends AbstractUpdateUseCase<SystemUserDomain, SystemUserDomain> {
    public SystemUserUpdateUseCase(EntityConverter converter, AbstractDao auditDao, AbstractDao entityDao) {
        super(converter, auditDao, entityDao);
    }

    @Override
    protected String getEntity() {
        return SystemModules.SYSTEM_USER.name();
    }

    @Override
    protected Filter prepareFilterToGetOldEntity(SystemUserDomain domain) {
        SystemUserFilter filter = new SystemUserFilter();
        filter.setId(domain.getId());
        return filter;
    }

    @Override
    protected SystemUserDomain prepareResponse(SystemUserDomain domain) {
        return domain;
    }
}
