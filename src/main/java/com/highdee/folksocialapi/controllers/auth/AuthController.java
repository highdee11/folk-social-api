package com.highdee.folksocialapi.controllers.auth;

import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.auth.UserSignInResponse;
import com.highdee.folksocialapi.enums.ResponseCode;
import com.highdee.folksocialapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create-account")
    public ResponseEntity<RestResponse<String>> createAccount(@Valid @RequestBody CreateUserRequest request){
        userService.create(request);

        return ResponseEntity
                .status(200)
                .body(RestResponse.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(@Valid @RequestBody UserLoginRequest request){
        try {
            UserSignInResponse response = userService.Login(request);
            return ResponseEntity.status(200).body(RestResponse.success(response));
        }catch (AuthenticationException exception){
            ResponseCode code = ResponseCode.INVALID_CREDENTIAL;
            return ResponseEntity.status(400).body(new RestResponse(code.getCode(), code.getMessage(), null));
        }
    }

}
