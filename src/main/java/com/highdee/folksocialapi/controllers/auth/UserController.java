package com.highdee.folksocialapi.controllers.auth;

import com.highdee.folksocialapi.dto.request.auth.UpdateUserRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.post.TagResponse;
import com.highdee.folksocialapi.dto.response.user.UserPreferenceResponse;
import com.highdee.folksocialapi.dto.response.user.UserResponse;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.services.auth.AuthService;
import com.highdee.folksocialapi.services.user.ProfileService;
import com.highdee.folksocialapi.services.user.UserPreferenceService;
import com.highdee.folksocialapi.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final UserPreferenceService preferenceService;

    private final ProfileService profileService;

    @Autowired
    private AuthService authService;

    public UserController(UserService userService, UserPreferenceService preferenceService, ProfileService profileService){
        this.userService = userService;
        this.preferenceService = preferenceService;
        this.profileService = profileService;
    }

    @GetMapping("")
    public ResponseEntity<RestResponse<Object>> getUser() throws AuthentionException {
        User user = authService.getLoggedInUser();
        return ResponseEntity.status(200).body(RestResponse.success(new UserResponse(user)));
    }

    @PutMapping("")
    public ResponseEntity<RestResponse> updateUser(@Valid @RequestBody UpdateUserRequest request) throws AuthentionException {
        User user = authService.getLoggedInUser();
        profileService.updateUser(user.getId(), request);
        return ResponseEntity.status(200).body(RestResponse.success(null));
    }

    @GetMapping("/preference")
    public ResponseEntity<RestResponse<UserPreferenceResponse>> userPreference() throws AuthentionException {
        User user = authService.getLoggedInUser();
        UserPreferenceResponse response = preferenceService.getUserPreference(user);
        return ResponseEntity.status(200).body(RestResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<RestResponse> searchUser(String search) {
        Page<UserResponse> userResponses = userService.searchUser(search);

        return ResponseEntity.status(200).body(RestResponse.success(userResponses));
    }

    @GetMapping("/suggest")
    public ResponseEntity<RestResponse<Page<UserResponse>>> suggestUser() throws AuthentionException {
        // Get Logged in user
        User loggedUser = authService.getLoggedInUser();

        // Retrieve user interest
        Set<TagResponse> tags = profileService.listInterest(loggedUser);
        Set<Long> tagIds = tags.stream().map(TagResponse::getId).collect(Collectors.toSet());

        Page<UserResponse> userResponses = userService.suggestUsers(loggedUser.getId(), tagIds);

        return ResponseEntity.status(200).body(RestResponse.success(userResponses));
    }

}
