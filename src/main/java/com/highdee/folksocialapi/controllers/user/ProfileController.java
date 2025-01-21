package com.highdee.folksocialapi.controllers.user;

import com.highdee.folksocialapi.dto.request.user.UpdateInterestRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.post.TagResponse;
import com.highdee.folksocialapi.enums.ResponseCode;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.services.tag.TagService;
import com.highdee.folksocialapi.services.user.ProfileService;
import com.highdee.folksocialapi.services.user.UserService;
import jakarta.validation.Valid;
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

    private final UserService userService;

    public ProfileController(TagService tagService, ProfileService profileService, UserService userService) {
        this.tagService = tagService;
        this.profileService = profileService;
        this.userService = userService;
    }

    @PostMapping("/interests/update")
    public ResponseEntity<RestResponse<Object>> updateUserTags(@Valid @RequestBody UpdateInterestRequest request) throws AuthentionException {
        Optional<User> user = userService.getLoggedInUser();
        if(user.isEmpty()) throw new AuthentionException(ResponseCode.AUTHENTICATION_ERROR.getMessage());

        List<Tag> tags = tagService.createAllTags(request.getInterests());
        profileService.updateInterest(tags, user.get().getId());

        return ResponseEntity.status(200).body(RestResponse.success(null));
    }

    @GetMapping("/interests")
    public ResponseEntity<RestResponse<Object>> updateUserTags() throws AuthentionException {
        Optional<User> user = userService.getLoggedInUser();
        if(user.isEmpty()) throw new AuthentionException(ResponseCode.AUTHENTICATION_ERROR.getMessage());
        Long userId = user.get().getId();
        Set<TagResponse> interests = profileService.listInterest(userId);

        return ResponseEntity.status(200).body(RestResponse.success(interests));
    }
}
