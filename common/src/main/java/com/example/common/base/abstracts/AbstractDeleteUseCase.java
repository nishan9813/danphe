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

public abstract class AbstractDeleteUseCase<D extends Domain, O> implements UseCase<D, O> {

  protected final EntityConverter converter;
  protected final AbstractDao entityDao;
  protected final AbstractDao auditDao;

  protected AbstractDeleteUseCase(
      EntityConverter converter, AbstractDao auditDao, AbstractDao entityDao) {
    this.converter = converter;
    this.auditDao = auditDao;
    this.entityDao = entityDao;
  }

  @Override
  public O execute(D req) {
    this.validate(req);
    D domain = this.prepareFilter(req);
    D oldDomain = this.getOldDomain(domain);
    this.preProcessing(domain, oldDomain);
    AuditDomain auditDomain = this.prepareAuditDomain(domain, oldDomain);
    this.insertIntoAuditLog(auditDomain);
    this.update(domain);
    this.postSuccess(domain, oldDomain);
    return prepareResponse(domain);
  }

  protected void validate(D filter) {}

  protected void preProcessing(D filter, D oldDomain) {}

  protected void postSuccess(D filter, D oldDomain) {}

  protected AuditDomain prepareAuditDomain(D domain, D oldDomain) {
    AuditDomain auditDomain = new AuditDomain();
    auditDomain.setId(StringUtils.generateUUID());
    auditDomain.setEntityKey(domain.getId());
    auditDomain.setEntity(this.getEntity());
    auditDomain.setOldEntity(Jsons.toJsonObj(oldDomain));
    auditDomain.setRequestedBy(getUser());
    auditDomain.setRequestedOn(LocalDateTime.now());
    auditDomain.setRespondedBy(getUser());
    auditDomain.setRespondedOn(LocalDateTime.now());
    auditDomain.setActive(true);
    auditDomain.setDeleted(false);
    auditDomain.setRequestType(RequestType.DELETE.name());
    auditDomain.setResource(RequestContextHolder.get().getResource());
    return auditDomain;
  }

  protected D getOldDomain(D domain) {
    Filter queryFilter = prepareFilterToGetOldEntity(domain);
    Optional<D> oldDomainOptional = this.entityDao.findOne(queryFilter);
    if (oldDomainOptional.isEmpty()) {
      throw new AppException("Record not found");
    }
    return oldDomainOptional.get();
  }

  protected void update(D domain) {
    int updateCount = this.entityDao.delete((Filter) converter.toEntity(domain));
    if (updateCount == 0) {
      throw new AppException("Entity Cannot be updated");
    }
  }

  protected O prepareResponse(D domain) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("id", domain.getId());
    return (O) responseMap;
  }

  protected abstract String getEntity();

  protected void insertIntoAuditLog(AuditDomain auditDomain) {
    int insertCount = this.auditDao.insert(converter.toEntity(auditDomain));
    if (insertCount == 0) {
      throw new AppException("AuditExceptionManager.AuditError.ENTITY_CAN_NOT_BE_INSERTED");
    }
  }

  protected abstract Filter prepareFilterToGetOldEntity(D filter);

  private D prepareFilter(D domain) {
    domain.setDeleted(true);
    domain.setActive(false);
    domain.setDeletedBy(getUser());
    domain.setDeletedOn(LocalDateTime.now());
    return domain;
  }

  private String getUser() {
    return RequestContextHolder.get().getId();
  }
}
