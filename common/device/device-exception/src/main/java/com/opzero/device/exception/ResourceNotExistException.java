package com.opzero.device.exception;

import com.opzero.core.exception.ExceptionCodes;

public class ResourceNotExistException extends RuntimeException {

    private final int statusCode;

    public ResourceNotExistException(String msg) {
        super(msg);
        statusCode = ExceptionCodes.RESOURCE_NOT_FOUND_EXCEPTION;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
