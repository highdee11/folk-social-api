package com.highdee.folksocialapi.services.auth;

import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.dto.response.auth.UserSignInResponse;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;

public interface AuthService {
    UserSignInResponse login(UserLoginRequest loginRequest);
    User getLoggedInUser() throws AuthentionException;
}
