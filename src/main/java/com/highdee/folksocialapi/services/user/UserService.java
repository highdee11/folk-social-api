package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.dto.response.auth.UserSignInResponse;
import com.highdee.folksocialapi.models.auth.User;

import java.util.Optional;

public interface UserService {
    public User create(CreateUserRequest createUserRequest);
    public UserSignInResponse login(UserLoginRequest loginRequest);
    public Optional<User> getLoggedInUser();
}
