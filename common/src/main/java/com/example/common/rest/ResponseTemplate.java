package com.example.common.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplate<T> {
    private String code;
    private String message;
    private T data;
    private String time;
}
