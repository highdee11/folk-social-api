package com.highdee.folksocialapi.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

public class UserLoginRequest {
    @NotBlank
    public String email;

    @NotBlank
    public String password;
}
