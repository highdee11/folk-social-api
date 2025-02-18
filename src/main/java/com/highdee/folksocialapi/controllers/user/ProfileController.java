package com.highdee.folksocialapi.controllers.user;

import com.highdee.folksocialapi.dto.request.user.UpdateInterestRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.post.TagResponse;
import com.highdee.folksocialapi.dto.response.user.ProfileStatsResponse;
import com.highdee.folksocialapi.dto.response.user.UserResponse;
import com.highdee.folksocialapi.enums.ResponseCode;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.services.auth.AuthService;
import com.highdee.folksocialapi.services.tag.TagService;
import com.highdee.folksocialapi.services.user.ProfileService;
import com.highdee.folksocialapi.services.user.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final TagService tagService;

    private final ProfileService profileService;

    private final AuthService authService;

    public ProfileController(TagService tagService, ProfileService profileService, AuthService authService) {
        this.tagService = tagService;
        this.profileService = profileService;
        this.authService = authService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<RestResponse<Object>> getProfileStats(@PathVariable String username) throws CustomException {
        UserResponse response = profileService.getProfile(username);
        return ResponseEntity.status(200).body(RestResponse.success(response));
    }

    @GetMapping("/stats/{userId}")
    public ResponseEntity<RestResponse<Object>> getProfileStats(@PathVariable long userId) {
        ProfileStatsResponse response = profileService.getProfileStats(userId);
        return ResponseEntity.status(200).body(RestResponse.success(response));
    }

    @PostMapping("/interests/update")
    public ResponseEntity<RestResponse<Object>> updateUserTags(@Valid @RequestBody UpdateInterestRequest request) throws AuthentionException {
        User user = authService.getLoggedInUser();
        List<Tag> tags = tagService.createAllTags(request.getInterests());
        profileService.updateInterest(tags, user.getId());

        return ResponseEntity.status(200).body(RestResponse.success(null));
    }

    @GetMapping("/interests")
    public ResponseEntity<RestResponse<Object>> listInterest() throws AuthentionException {
        User user = authService.getLoggedInUser();
        Set<TagResponse> interests = profileService.listInterest(user);

        return ResponseEntity.status(200).body(RestResponse.success(interests));
    }
}
