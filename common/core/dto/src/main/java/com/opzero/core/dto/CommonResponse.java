package com.opzero.core.dto;

import lombok.Data;

@Data
public class CommonResponse<T> {
    public T data;
    public String message;
    public int code;


    public CommonResponse() {
    }

    public CommonResponse(T data, String message, int code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public CommonResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public CommonResponse(T data) {
        this.data = data;
        this.code = 200;
        this.message = "Success";
    }

    public CommonResponse(String message) {
        this.message = message;
    }

    public CommonResponse(int code) {
        this.code = code;
    }

    public CommonResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public CommonResponse(T data, int code) {
        this.data = data;
        this.code = code;
    }
}
