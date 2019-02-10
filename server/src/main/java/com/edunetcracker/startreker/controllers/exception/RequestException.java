package com.edunetcracker.startreker.controllers.exception;

public class RequestException extends RuntimeException {

    private String errorMessage;

    public RequestException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
