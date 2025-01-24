package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.dto.response.auth.UserSignInResponse;
import com.highdee.folksocialapi.dto.response.user.UserResponse;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface UserService {
    User create(CreateUserRequest createUserRequest);
    UserSignInResponse login(UserLoginRequest loginRequest);
    User getLoggedInUser() throws AuthentionException;
    boolean userExistByUsername(String username);
    Page<UserResponse> searchUser(String name);
    List<UserResponse> suggestUsers(Set<Long> tags);
}
