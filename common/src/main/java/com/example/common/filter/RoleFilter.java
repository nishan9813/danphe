package com.example.common.filter;

import com.example.common.filter.sorting.RoleSortField;
import lombok.Data;

@Data
public class RoleFilter extends AbstractFilter{
    private String title;

    @Override
    public String getSortColumn() {
        return RoleSortField.getColumn(sortField);
    }

}
