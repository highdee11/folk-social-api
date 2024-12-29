package com.highdee.folksocialapi.enums;

public enum ResponseCode {
    // Success
    REQUEST_SUCCESSFUL("SUC001", "Request was successful"),

    // Errors
    SERVER_ERROR("ERR500", "An error occured!"),
    VALIDATION_ERROR("ERR001", "Validation error occurred");

    private final String code;
    private final String message;

    ResponseCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}