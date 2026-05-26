package com.example.common.base.abstracts;



import com.example.common.base.AbstractDao;
import com.example.common.base.Domain;
import com.example.common.base.EntityConverter;
import com.example.common.base.UseCase;
import com.example.common.base.audits.AuditDomain;
import com.example.common.context.RequestContextHolder;
import com.example.common.converter.AuditConvertor;
import com.example.common.exception.AppException;
import com.example.common.rest.constant.RequestType;
import com.example.common.utils.Jsons;
import com.example.common.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAddUseCase<D extends Domain, O> implements UseCase<D, O> {

  protected final EntityConverter converter;
  protected final AbstractDao entityDao;
  protected final AbstractDao auditDao;

  protected AbstractAddUseCase(
          EntityConverter converter, AbstractDao auditDao, AbstractDao entityDao) {
    this.converter = converter;
    this.auditDao = auditDao;
    this.entityDao = entityDao;
  }

  @Override
  public O execute(D req) {
    this.validate(req);
    D domain = this.prepareDomain(req);
    this.preProcessing(domain);
    AuditDomain auditDomain = this.prepareAuditDomain(domain);
    auditDomain.setResource(RequestContextHolder.isPresent() ? RequestContextHolder.get().getResource() : null);
    this.insertIntoAuditLog(auditDomain);
    this.insert(domain);
    this.postSuccess(domain);
    return prepareResponse(domain);
  }

  protected void validate(D domain) {}

  protected void preProcessing(D domain) {}

  protected AuditDomain prepareAuditDomain(D domain) {
    AuditDomain auditDomain = new AuditDomain();
    auditDomain.setId(StringUtils.generateUUID());
    auditDomain.setEntityKey(domain.getId());
    auditDomain.setEntity(this.getEntity());
    auditDomain.setNewEntity(Jsons.toJsonObj(domain));
    auditDomain.setRequestedBy(getUser());
    auditDomain.setRequestedOn(LocalDateTime.now());
    auditDomain.setRespondedBy(getUser());
    auditDomain.setRespondedOn(LocalDateTime.now());
    auditDomain.setActive(true);
    auditDomain.setDeleted(false);
    auditDomain.setRequestType(RequestType.ADD.name());
    return auditDomain;
  }

  protected D prepareDomain(D domain) {
    if (domain.getId() == null || domain.getId().isBlank()) {
      domain.setId(StringUtils.generateUUID());
    }
    domain.setCreatedBy(this.getUser());
    domain.setCreatedOn(LocalDateTime.now());
    domain.setLastModifiedBy(this.getUser());
    domain.setLastModifiedOn(LocalDateTime.now());
    domain.setActive(true);
    domain.setDeleted(false);
    domain.setVersion(0L);
    return domain;
  }

  protected abstract String getEntity();

  protected void insertIntoAuditLog(AuditDomain auditDomain) {
    AuditConvertor auditConvertor = new AuditConvertor();
    int insertCount = this.auditDao.insert(auditConvertor.toEntity(auditDomain));
//    if (insertCount == 0) {
//      throw new AppException("AuditExceptionManager.AuditError.ENTITY_CAN_NOT_BE_INSERTED");
//    }
  }

  protected void insert(Domain domain) {
    int insertCount = this.entityDao.insert(converter.toEntity(domain));
    if (insertCount == 0) {
      throw new AppException("Entity Cannot be added");
    }
  }

  protected void postSuccess(D domain) {}

  protected O prepareResponse(D domain) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("id", domain.getId());
    return (O) responseMap;
  }

  private String getUser() {
    if (RequestContextHolder.isPresent()) {
      return RequestContextHolder.get().getId();
    }
    return "System";
  }
}
