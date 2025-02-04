package com.highdee.folksocialapi.dto.response.auth;

import com.highdee.folksocialapi.models.auth.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSignInResponse {
    public Long id;
    public String email;
    public String firstname;
    public String lastname;
    public String token;

    public UserSignInResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
    }
}
