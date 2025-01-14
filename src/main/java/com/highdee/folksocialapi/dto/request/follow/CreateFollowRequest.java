package com.highdee.folksocialapi.dto.request.follow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateFollowRequest {
    @NotNull(message = "Following Id is required")
    public Long followingId;

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowing_id(Long followingId) {
        this.followingId = followingId;
    }
}
