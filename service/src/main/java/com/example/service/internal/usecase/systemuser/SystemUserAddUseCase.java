package com.example.service.internal.usecase.systemuser;

import com.example.common.base.AbstractDao;
import com.example.common.base.EntityConverter;
import com.example.common.base.abstracts.AbstractAddUseCase;
import com.example.common.domain.SystemUserDomain;
import com.example.common.exception.AppException;
import com.example.common.permissions.systemUser.SystemUserRole;
import com.example.common.rest.constant.SystemModules;

public class SystemUserAddUseCase extends AbstractAddUseCase<SystemUserDomain, SystemUserDomain> {
    public SystemUserAddUseCase(EntityConverter converter, AbstractDao auditDao, AbstractDao entityDao) {
        super(converter, auditDao, entityDao);
    }

//    @Override
//    protected void validate(SystemUserDomain req) {
//        if (req.getRoleId() != null) {
//            roleRepository.findById(req.getRoleId())
//                    .orElseThrow(() -> new AppException("Role not found with id: " + req.getRoleId()));
//        }
//    }

    @Override
    protected String getEntity(){
        return SystemModules.SYSTEM_USER.getEntity();
    }

    @Override
    protected SystemUserDomain prepareResponse(SystemUserDomain domain){
        return domain;
    }

    @Override
    protected SystemUserDomain prepareDomain(SystemUserDomain domain) {
        SystemUserDomain request = super.prepareDomain(domain);
        request.setRole(SystemUserRole.SUPER_ADMIN);
        return domain;
    }
}
