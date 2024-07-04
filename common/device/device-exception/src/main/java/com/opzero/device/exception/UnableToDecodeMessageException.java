package com.opzero.device.exception;

import com.opzero.core.exception.ExceptionCodes;

public class UnableToDecodeMessageException extends RuntimeException {
    private final int statusCode;

    public UnableToDecodeMessageException(String msg) {
        super(msg);
        statusCode = ExceptionCodes.DEVICE_NOT_FOUND;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
