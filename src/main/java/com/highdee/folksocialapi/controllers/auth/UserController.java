package com.highdee.folksocialapi.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/details")
    public ResponseEntity getUser(@AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.status(200).body(userDetails.getUsername());
    }
}
