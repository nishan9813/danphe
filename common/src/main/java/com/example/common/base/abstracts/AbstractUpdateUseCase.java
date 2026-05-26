package com.example.common.base.abstracts;


import com.example.common.base.AbstractDao;
import com.example.common.base.Domain;
import com.example.common.base.EntityConverter;
import com.example.common.base.Filter;
import com.example.common.base.UseCase;
import com.example.common.base.audits.AuditDomain;
import com.example.common.context.RequestContextHolder;
import com.example.common.exception.AppException;
import com.example.common.rest.constant.RequestType;
import com.example.common.utils.Jsons;
import com.example.common.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractUpdateUseCase<D extends Domain, O> implements UseCase<D, O> {

  protected final EntityConverter converter;
  protected final AbstractDao entityDao;
  protected final AbstractDao auditDao;

  protected AbstractUpdateUseCase(
      EntityConverter converter, AbstractDao auditDao, AbstractDao entityDao) {
    this.converter = converter;
    this.auditDao = auditDao;
    this.entityDao = entityDao;
  }

  @Override
  public O execute(D req) {
    this.validate(req);
    D domain = this.prepareDomain(req);
    D oldDomain = this.getOldDomain(domain);
    domain.setVersion(oldDomain.getVersion() + 1);
    this.preProcessing(domain, oldDomain);
    AuditDomain auditDomain = this.prepareAuditDomain(domain, oldDomain);
    auditDomain.setResource(RequestContextHolder.isPresent() ? RequestContextHolder.get().getResource() : null);
    this.insertIntoAuditLog(auditDomain);
    this.update(domain);
    this.postSuccess(domain, oldDomain);
    return prepareResponse(domain);
  }

  protected void validate(D domain) {}

  protected void preProcessing(D domain, D oldDomain) {}

  protected AuditDomain prepareAuditDomain(D domain, D oldDomain) {
    AuditDomain auditDomain = new AuditDomain();
    auditDomain.setId(StringUtils.generateUUID());
    auditDomain.setEntityKey(domain.getId());
    auditDomain.setEntity(this.getEntity());
    auditDomain.setOldEntity(Jsons.toJsonObj(oldDomain));
    auditDomain.setNewEntity(Jsons.toJsonObj(domain));
    auditDomain.setRequestedBy(getUser());
    auditDomain.setRequestedOn(LocalDateTime.now());
    auditDomain.setRespondedBy(getUser());
    auditDomain.setRespondedOn(LocalDateTime.now());
    auditDomain.setActive(true);
    auditDomain.setDeleted(false);
    auditDomain.setRequestType(RequestType.UPDATE.name());
    return auditDomain;
  }

  protected D prepareDomain(D domain) {
    domain.setLastModifiedBy(getUser());
    domain.setLastModifiedOn(LocalDateTime.now());
    domain.setActive(true);
    domain.setDeleted(false);
    return domain;
  }

  protected abstract String getEntity();

  protected void insertIntoAuditLog(AuditDomain auditDomain) {
    int insertCount = this.auditDao.insert(converter.toEntity(auditDomain));
    if (insertCount == 0) {
      throw new AppException("AuditExceptionManager.AuditError.ENTITY_CAN_NOT_BE_INSERTED");
    }
  }

  protected void update(D domain) {
    int updateCount = this.entityDao.update(converter.toEntity(domain));
    if (updateCount == 0) {
      throw new AppException("Entity Cannot be updated");
    }
  }

  protected void postSuccess(D domain, D oldDomain) {}

  protected O prepareResponse(D domain) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("id", domain.getId());
    return (O) responseMap;
  }

  protected D getOldDomain(D domain) {
    Filter filter = this.prepareFilterToGetOldEntity(domain);
    Optional<D> oldDomainOptional = this.entityDao.findOne(filter);
    if (oldDomainOptional.isEmpty()) {
      throw new AppException("Record not found");
    }
    return oldDomainOptional.get();
  }

  protected abstract Filter prepareFilterToGetOldEntity(D domain);

  private String getUser() {
    return RequestContextHolder.get().getId();
  }
}
