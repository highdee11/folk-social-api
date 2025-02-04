package com.highdee.folksocialapi.dto.response.user;

import com.highdee.folksocialapi.models.auth.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserResponse implements Serializable {
    public Long id;
    public String email;
    public String firstname;
    public String lastname;
    public String username;

    public UserResponse() {}

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.username = user.getUsername();
    }

}
