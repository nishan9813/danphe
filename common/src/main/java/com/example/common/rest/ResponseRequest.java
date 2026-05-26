package com.example.common.rest;

import com.example.common.exception.AppException;
import com.example.common.exception.ForbiddenException;
import com.example.common.rest.constant.ApiConstants;
import com.example.common.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class ResponseRequest {

    public static <T>ResponseEntity<ResponseTemplate<T>> ok(T data) {
        return ResponseEntity.ok(of(data));
    }

    public static <T> ResponseTemplate<T> of(T data) {
        ResponseTemplate<T> responseTemplate = new ResponseTemplate<>();
        responseTemplate.setData(data);
        responseTemplate.setCode(ApiConstants.SUCCESS_CODE);
        responseTemplate.setMessage(ApiConstants.SUCCESS);
        responseTemplate.setTime(DateUtils.now());
        return responseTemplate;
    }

    public static <T extends AppException> ResponseEntity<ResponseTemplate<String>> error(T data) {
        ResponseTemplate<String> responseTemplate = new ResponseTemplate<>();
        responseTemplate.setCode(data.getExceptionType().getCode());
        responseTemplate.setMessage(data.getExceptionType().getDescription());
        responseTemplate.setData(DateUtils.now());
        return new ResponseEntity<>(responseTemplate, HttpStatus.BAD_REQUEST);
    }

    public static <T extends ForbiddenException> ResponseEntity<ResponseTemplate<String>> forbiddenError(T data) {
        ResponseTemplate<String> responseTemplate = new ResponseTemplate<>();
        responseTemplate.setCode(ApiConstants.ERROR_CODE);
        responseTemplate.setMessage(data.getMessage());
        responseTemplate.setTime(DateUtils.now());
        return new ResponseEntity<>(responseTemplate, HttpStatus.FORBIDDEN);
    }
}
