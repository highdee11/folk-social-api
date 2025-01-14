package com.highdee.folksocialapi.services.follow;

import com.highdee.folksocialapi.dto.request.follow.CreateFollowRequest;
import com.highdee.folksocialapi.dto.request.follow.UnFollowRequest;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.models.UserFollow;
import com.highdee.folksocialapi.models.auth.User;

public interface FollowService {
    public void createFollow(CreateFollowRequest request, User follower) throws CustomException;

    public void unFollow(UnFollowRequest request, User follower) throws CustomException;

    public boolean checkIfAlreadyFollower(Long followerId, Long FollowedId);
    public UserFollow getFollow(Long followerId, Long FollowedId);
}
