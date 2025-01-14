package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.dto.response.auth.UserSignInResponse;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import com.highdee.folksocialapi.services.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    final AuthenticationManager authenticationManager;

    final JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public User create(CreateUserRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setEmail(request.email);
        user.setUsername(request.getUsername());
        user.setFirstName(request.firstname);
        user.setLastName(request.lastname);
        user.setPassword(encoder.encode(request.password));

        if(request.dob != null) {
            user.setDob(request.dob);
        }

        return userRepository.save(user);
    }

    @Override
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

    @Override
    public Optional<User> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userRepository.findById(Long.parseLong(userId));
    }
}
