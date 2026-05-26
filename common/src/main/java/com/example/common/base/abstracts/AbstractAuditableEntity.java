package com.example.common.base.abstracts;

import com.example.common.base.audits.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AbstractAuditableEntity extends AuditEntity {

  private String newEntity;
  private String oldEntity;
}
