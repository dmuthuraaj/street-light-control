package com.opzero.device.exception;

import com.opzero.core.exception.ExceptionCodes;

public class DuplicateResourceException extends RuntimeException {

    private final int statusCode;

    public DuplicateResourceException(String msg) {
        super(msg);
        statusCode = ExceptionCodes.DUPLICATE_RESOURCE_EXCEPTION;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
