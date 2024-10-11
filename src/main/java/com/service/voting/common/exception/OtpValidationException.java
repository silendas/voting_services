package com.service.voting.common.exception;

public class OtpValidationException extends RuntimeException{
    public OtpValidationException(String message) {
        super(message);
    }
}
