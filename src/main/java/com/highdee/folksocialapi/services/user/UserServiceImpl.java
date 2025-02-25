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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    final JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
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

    public Page<UserResponse> suggestUsers(Long userId, Set<Long> interestIds){
        int page = 0;
        int size = AppConstants.MAX_PAGE_SIZE;
        Sort sort = Sort.by(AppConstants.DEFAULT_PAGE_ORDERBY).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<User> users = userRepository.findUserByInterest(pageRequest, userId , interestIds);
        return users.map(UserResponse::new);
    }

}
