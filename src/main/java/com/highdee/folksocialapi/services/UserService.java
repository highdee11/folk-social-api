package com.highdee.folksocialapi.services;

import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.dto.response.auth.UserSignInResponse;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserRepository userRepository;

    final AuthenticationManager authenticationManager;

    final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User create(CreateUserRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setEmail(request.email);
        user.setFirstName(request.firstname);
        user.setLastName(request.lastname);
        user.setPassword(encoder.encode(request.password));

        if(request.dob != null) {
            user.setDob(request.dob);
        }

        return userRepository.save(user);
    }

    public UserSignInResponse login(UserLoginRequest loginRequest){

        // Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password));

        // Retrieve user
        User user = userRepository.findByEmail(loginRequest.email);
        String token = jwtService.generateToken(user.getEmail());

        // Build login response
        UserSignInResponse userSignInResponse = new UserSignInResponse();
        userSignInResponse.setId(String.valueOf(user.getId()));
        userSignInResponse.setEmail(user.getEmail());
        userSignInResponse.setFirstname(user.getFirstName());
        userSignInResponse.setLastname(user.getLastName());
        userSignInResponse.setToken(token);


        return userSignInResponse;
    }
}
