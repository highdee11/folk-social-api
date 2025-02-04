package com.highdee.folksocialapi.unit.services;

import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.dto.response.auth.UserSignInResponse;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import com.highdee.folksocialapi.services.auth.AuthService;
import com.highdee.folksocialapi.services.auth.AuthServiceImpl;
import com.highdee.folksocialapi.services.auth.JwtService;
import com.highdee.folksocialapi.services.auth.JwtServiceImpl;
import com.highdee.folksocialapi.services.user.UserService;
import com.highdee.folksocialapi.services.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class AuthServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtServiceImpl jwtService;

    @InjectMocks
    AuthServiceImpl authService;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@gmail.com");
        createUserRequest.setUsername("myusername");
        createUserRequest.setFirstname("firstname");
        createUserRequest.setLastname("lastname");
        createUserRequest.setDob(LocalDate.now());
        createUserRequest.setPassword("secret");

        User savedUser = new User();
        savedUser.setEmail("test@gmail.com");
        savedUser.setFirstName("firstname");
        savedUser.setUsername("myusername");
        savedUser.setLastName("lastname");
        savedUser.setDob(LocalDate.now());
        savedUser.setPassword("secret");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User user = userService.create(createUserRequest);

        assertEquals("test@gmail.com", user.getEmail());
        assertEquals("firstname", user.getFirstName());
        assertEquals("lastname", user.getLastName());
    }

    @Test
    void testValidLogin(){
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@gmail.com");
        userLoginRequest.setPassword("secret");

        User savedUser = new User();
        savedUser.setEmail("test@gmail.com");
        savedUser.setFirstName("firstname");
        savedUser.setUsername("myusername");
        savedUser.setLastName("lastname");
        savedUser.setDob(LocalDate.now());
        savedUser.setPassword("secret");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail(any(String.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(String.class))).thenReturn("jwt-token");

        UserSignInResponse userSignInResponse = authService.login(userLoginRequest);

        assertEquals("test@gmail.com", userSignInResponse.email);
        assertEquals("jwt-token", userSignInResponse.token);
    }

    @Test
    void testInValidLogin(){
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@gmail.com");
        userLoginRequest.setPassword("secret");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException(""));

        assertThrows(AuthenticationException.class, ()-> authService.login(userLoginRequest));
    }
}
