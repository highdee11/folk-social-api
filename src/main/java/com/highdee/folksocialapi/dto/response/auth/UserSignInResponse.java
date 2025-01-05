package com.highdee.folksocialapi.dto.response.auth;

public class UserSignInResponse {
    public String id;
    public String email;
    public String firstname;
    public String lastname;
    public String token;

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
