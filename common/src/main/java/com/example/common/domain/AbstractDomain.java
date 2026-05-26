package com.example.common.domain;

import com.example.common.base.Domain;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AbstractDomain implements Domain {
    protected String id;
    protected LocalDateTime createdOn;
    protected String createdBy;
    protected LocalDateTime lastModifiedOn;
    protected String lastModifiedBy;
    protected LocalDateTime deletedOn;
    protected String deletedBy;
    protected boolean active = true;
    protected boolean deleted = false;
    protected String remark;
    protected Long version;
}
