package com.highdee.folksocialapi.controllers;

import com.highdee.folksocialapi.dto.request.follow.CreateFollowRequest;
import com.highdee.folksocialapi.dto.request.follow.UnFollowRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.exceptions.handlers.ResourceNotFoundException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.services.auth.AuthService;
import com.highdee.folksocialapi.services.follow.FollowService;
import com.highdee.folksocialapi.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    private final AuthService authService;

    public FollowController(FollowService followService, AuthService authService) {
        this.followService = followService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<RestResponse<Object>> createFollow(@Valid @RequestBody CreateFollowRequest request) throws CustomException, AuthentionException {
        User follower = authService.getLoggedInUser();
        followService.createFollow(request, follower);
        return ResponseEntity.status(200).body(RestResponse.success(null));
    }

    @DeleteMapping
    public ResponseEntity<RestResponse<Object>> unFollow(@Valid @RequestBody UnFollowRequest request) throws CustomException, AuthentionException {
        User follower = authService.getLoggedInUser();
        followService.unFollow(request, follower);
        return ResponseEntity.status(200).body(RestResponse.success(null));
    }

}
