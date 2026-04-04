package com.example.demo.dto.response;

public class RegistrationResponse {
    private boolean success;
    private String message;
    private String error;

    public RegistrationResponse(boolean success, String message, String error) {
        this.success = success;
        this.message = message;
        this.error = error;
    }

    public RegistrationResponse(boolean success, String message) {
        this(success, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}