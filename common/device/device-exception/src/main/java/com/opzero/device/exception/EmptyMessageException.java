package com.opzero.device.exception;

import com.opzero.core.exception.ExceptionCodes;

public class EmptyMessageException extends RuntimeException {
    private final int statusCode;

    public EmptyMessageException(String msg) {
        super(msg);
        statusCode = ExceptionCodes.DEVICE_NOT_FOUND;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
