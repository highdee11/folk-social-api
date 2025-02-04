package com.highdee.folksocialapi.services.auth;

import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.dto.response.auth.UserSignInResponse;
import com.highdee.folksocialapi.enums.ResponseCode;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    @Override
    public UserSignInResponse login(UserLoginRequest loginRequest){

        // Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password));

        // Retrieve user
        User user = userRepository.findByEmail(loginRequest.email);
        String token = jwtService.generateToken(user.getEmail());

        // Build login response
        UserSignInResponse userSignInResponse = new UserSignInResponse(user);
        userSignInResponse.setToken(token);

        return userSignInResponse;
    }

    @Override
    public User getLoggedInUser() throws AuthentionException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userRepository.findById(Long.parseLong(userId))
                .orElseThrow(()-> new AuthentionException(ResponseCode.AUTHENTICATION_ERROR.getMessage()));
    }
}
