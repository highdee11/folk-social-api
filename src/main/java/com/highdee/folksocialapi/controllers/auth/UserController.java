package com.highdee.folksocialapi.controllers.auth;

import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.user.UserPreferenceResponse;
import com.highdee.folksocialapi.dto.response.user.UserResponse;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.services.user.UserPreferenceService;
import com.highdee.folksocialapi.services.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final UserPreferenceService preferenceService;

    public UserController(UserService userService, UserPreferenceService preferenceService){
        this.userService = userService;
        this.preferenceService = preferenceService;
    }

    @GetMapping("")
    public ResponseEntity<RestResponse<Object>> getUser() throws AuthentionException {
        User user = userService.getLoggedInUser();
        return ResponseEntity.status(200).body(RestResponse.success(new UserResponse(user)));
    }

    @GetMapping("/preference")
    public ResponseEntity<RestResponse<UserPreferenceResponse>> userPreference() throws AuthentionException {
        User user = userService.getLoggedInUser();
        UserPreferenceResponse response = preferenceService.getUserPreference(user);
        return ResponseEntity.status(200).body(RestResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<RestResponse> searchUser(String search) {
        Page<UserResponse> userResponses = userService.searchUser(search);

        return ResponseEntity.status(200).body(RestResponse.success(userResponses));
    }
}
