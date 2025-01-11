package com.highdee.folksocialapi.dto.response.auth;

import com.highdee.folksocialapi.models.auth.User;

public class UserResponse {
    public Long id;
    public String email;
    public String firstname;
    public String lastname;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
    }
}
