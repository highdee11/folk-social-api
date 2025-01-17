package com.highdee.folksocialapi.controllers.auth;

import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.auth.UserResponse;
import com.highdee.folksocialapi.enums.ResponseCode;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<RestResponse<Object>> getUser(@AuthenticationPrincipal UserDetails userDetails) throws AuthentionException {
        Optional<User> user = userService.getLoggedInUser();

        if(user.isEmpty()) throw new AuthentionException(ResponseCode.AUTHENTICATION_ERROR.getMessage());

        return ResponseEntity.status(200).body(RestResponse.success(new UserResponse(user.get())));
    }
}
