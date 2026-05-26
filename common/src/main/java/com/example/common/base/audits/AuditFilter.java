package com.example.common.base.audits;

import com.example.common.filter.AbstractFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuditFilter extends AbstractFilter {
    private String id;
    private List<String> ids;
    private int pageSize;
    private int pageNumber;
    private String sortField;
    private String sortOrder;
    private Boolean active;
    private String searchParameter;
    private Boolean delete;
}
