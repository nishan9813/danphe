package com.example.common.entity;

import com.example.common.base.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AbstractEntity implements Entity {
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
