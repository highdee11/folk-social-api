package com.highdee.folksocialapi.controllers.auth;

import com.highdee.folksocialapi.dto.request.auth.CreateUserRequest;
import com.highdee.folksocialapi.dto.request.auth.UserLoginRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.enums.ResponseCode;
import com.highdee.folksocialapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
                .body(new RestResponse<>(
                    ResponseCode.REQUEST_SUCCESSFUL.getCode(),
                    ResponseCode.REQUEST_SUCCESSFUL.getMessage(),
                    null)
                );
    }


}
