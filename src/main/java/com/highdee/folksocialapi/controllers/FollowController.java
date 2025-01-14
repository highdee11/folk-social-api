package com.highdee.folksocialapi.controllers;

import com.highdee.folksocialapi.dto.request.CreateFollowRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.exceptions.handlers.ResourceNotFoundException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.services.follow.FollowService;
import com.highdee.folksocialapi.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private FollowService followService;

    private UserService userService;

    public FollowController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<RestResponse<Object>> createFollow(@Valid @RequestBody CreateFollowRequest request) throws CustomException {
        User follower = userService.getLoggedInUser().orElseThrow(()-> new ResourceNotFoundException("Follower was not found"));
        followService.createFollow(request, follower);
        return ResponseEntity.status(200).body(RestResponse.success(null));
    }

}
