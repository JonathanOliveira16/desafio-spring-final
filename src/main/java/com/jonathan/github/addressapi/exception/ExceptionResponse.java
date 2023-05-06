package com.jonathan.github.addressapi.exception;

import lombok.Data;

@Data
public class ExceptionResponse {
    private String errorMessage;
    private String requestedURI;

    private int statusCode;

    public void callerURL(final String requestedURI) {
        this.requestedURI = requestedURI;
    }
}
