package com.highdee.folksocialapi.dto.response;

import org.springframework.lang.Nullable;

public class RestResponse<T> {
    public String code;

    public String message;

    public @Nullable T data;

    public RestResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
