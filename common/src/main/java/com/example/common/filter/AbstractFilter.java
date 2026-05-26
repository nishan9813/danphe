package com.example.common.filter;

import com.example.common.base.Filter;
import com.example.common.utils.StringUtils;
import lombok.Data;

import java.util.List;

@Data
public abstract class AbstractFilter implements Filter {
    protected String id;
    protected List<String> ids;
    protected int pageSize = 10;
    protected int pageNumber = 1;
    protected Boolean delete = false;
    protected String sortField;
    protected String sortOrder;
    protected Boolean active = true;
    protected String searchParameter;


    public String getSortColumn() {
        return "created_on";
    }

    public String getSortDirection() {
        if ("ASC".equalsIgnoreCase(sortOrder)) {
            return "ASC";
        }
        return "DESC";
    }

    public String getSearchParameter() {
        if (!StringUtils.isBlankOrNull(searchParameter)) {
            return "%" + searchParameter + "%";
        }
        return searchParameter;
    }

    public int getOffset() {
        int page = Math.max(1, pageNumber);
        return (page - 1) * Math.max(1, pageSize);
    }
}
