package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.constants.AppConstants;
import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.dto.response.auth.UserSignInResponse;
import com.highdee.folksocialapi.dto.response.user.UserResponse;
import com.highdee.folksocialapi.enums.ResponseCode;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import com.highdee.folksocialapi.services.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    public User getLoggedInUser() throws AuthentionException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

         return userRepository.findById(Long.parseLong(userId))
                .orElseThrow(()-> new AuthentionException(ResponseCode.AUTHENTICATION_ERROR.getMessage()));
    }

    @Override
    public boolean userExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Page<UserResponse> searchUser(String name) {
        int page = 0;
        int size = AppConstants.MAX_PAGE_SIZE;
        Sort sort = Sort.by(AppConstants.DEFAULT_PAGE_ORDERBY).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<User> results = userRepository.findAllByUsernameContainingIgnoreCase(pageRequest, name);
        System.out.println(results.getContent().size());
        return results.map(UserResponse::new);
    }

    public List<UserResponse> suggestUsers(Set<Long> interestIds){
        List<User> users = userRepository.findUserByInterest(interestIds);
        return users.stream().map(UserResponse::new).toList();
    }

}
