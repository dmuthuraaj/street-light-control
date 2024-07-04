package com.opzero.core.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final int statueCode;

    public BadRequestException(String message) {
        super(message);
        this.statueCode = ExceptionCodes.BAD_REQUEST;
    }


}
