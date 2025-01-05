package com.highdee.folksocialapi.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/details")
    public ResponseEntity getUser(){
        return ResponseEntity.status(200).body("");
    }
}
