package com.example.common.filter;

import com.example.common.filter.sorting.SystemUserSortField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemUserFilter extends AbstractFilter {

    private String email;

    @Override
    public String getSortColumn() {
        return SystemUserSortField.getColumn(sortField);
    }

    public static SystemUserFilter empty() {
        return new SystemUserFilter();
    }
}