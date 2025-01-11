package com.highdee.folksocialapi.dto.response;

import com.highdee.folksocialapi.enums.ResponseCode;
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

    public static<T> RestResponse<T> success(T data){
        return new RestResponse(
                ResponseCode.REQUEST_SUCCESSFUL.getCode(),
                ResponseCode.REQUEST_SUCCESSFUL.getMessage(),
                data
        );
    }

    public T getData() {
        return data;
    }
}
