package com.highdee.folksocialapi.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

public class UserLoginRequest {
    @NotBlank
    public String email;

    @NotBlank
    public String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
