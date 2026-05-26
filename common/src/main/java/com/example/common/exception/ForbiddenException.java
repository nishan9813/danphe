package com.example.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }

    @AllArgsConstructor
    @Getter
    public enum ForbiddenError implements ExceptionType {
        NOT_AUTHORIZED("123-ASD", "Not Authorized");
        private final String code;
        private final String description;
    }
}
