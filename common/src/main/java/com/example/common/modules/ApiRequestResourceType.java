package com.example.common.modules;

public enum ApiRequestResourceType {

    WEB_CLIENT("WEB-CLIENT"),
    WEB_SYSTEM("WEB-SYSTEM"),
    MOBILE_CLIENT("MOBILE-CLIENT");

    private final String value;

    ApiRequestResourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
