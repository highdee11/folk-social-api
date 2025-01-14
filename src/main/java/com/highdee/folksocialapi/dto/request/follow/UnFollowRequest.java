package com.highdee.folksocialapi.dto.request.follow;

import jakarta.validation.constraints.NotNull;

public class UnFollowRequest {
    @NotNull(message = "Following Id is required")
    public Long followingId;

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowing_id(Long followingId) {
        this.followingId = followingId;
    }
}
