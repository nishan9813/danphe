package com.example.common.filter.sorting;

public enum RoleSortField implements SortField{
    ID("id", "id"),
    TITLE("title", "title"),
    CREATED_ON("createdOn", "created_on"),
    CREATED_BY("createdBy", "created_by"),
    LAST_MODIFIED_ON("lastModifiedOn", "last_modified_on"),
    LAST_MODIFIED_BY("lastModifiedBy", "last_modified_by"),
    ACTIVE("active", "active"),
    VERSION("version", "version");

    private static final String DEFAULT_COLUMN = "created_on";

    private final String fieldName;
    private final String column;

    RoleSortField(String fieldName, String column) {
        this.fieldName = fieldName;
        this.column = column;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String getColumn() {
        return column;
    }

    public static String getColumn(String fieldName) {
        if (fieldName == null || fieldName.isBlank()) return DEFAULT_COLUMN;
        for (RoleSortField sf : values()) {
            if (sf.fieldName.equals(fieldName)) return sf.column;
        }
        return DEFAULT_COLUMN;
    }
}
