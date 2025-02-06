package com.highdee.folksocialapi.services.follow;

import com.highdee.folksocialapi.constants.CacheConst;
import com.highdee.folksocialapi.dto.request.follow.CreateFollowRequest;
import com.highdee.folksocialapi.dto.request.follow.UnFollowRequest;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.exceptions.handlers.ResourceNotFoundException;
import com.highdee.folksocialapi.models.UserFollow;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.repositories.FollowRepository;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService{

    private UserRepository userRepository;

    private FollowRepository followRepository;

    public FollowServiceImpl(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }


    @Override
    public void createFollow(CreateFollowRequest request, User follower) throws CustomException {
        User followedUser = userRepository.findById(request.getFollowingId())
                .orElseThrow(()-> new ResourceNotFoundException("Unknown user!"));

        if(followedUser.getId() == follower.getId()) throw new CustomException("You cannot follow yourself");

        if(checkIfAlreadyFollower(follower.getId(), followedUser.getId())){
            throw new CustomException("You're already a following "+followedUser.getUsername());
        }

        UserFollow userFollow = new UserFollow();
        userFollow.setFollower(follower);
        userFollow.setFollowing(followedUser);

        followRepository.save(userFollow);
    }

    @Override
    public void unFollow(UnFollowRequest request, User follower) throws CustomException {
        User followedUser = userRepository.findById(request.getFollowingId())
                .orElseThrow(()-> new ResourceNotFoundException("Unknown user!"));

        if(followedUser.getId() == follower.getId()) throw new CustomException("You cannot unfollow yourself");

        UserFollow userFollow = getFollow(follower.getId(), followedUser.getId());
        if(userFollow == null){
            throw new CustomException("you were never a follower of "+followedUser.getUsername());
        }

        followRepository.delete(userFollow);
    }

    @Override
    public boolean checkIfAlreadyFollower(Long followerId, Long followedId) {
        UserFollow userFollow =  getFollow(followerId, followedId);
        return userFollow != null;
    }

    @Override
    public UserFollow getFollow(Long followerId, Long followedId) {
        return followRepository.findByFollowedIdAndFollowerId(followedId, followerId);
    }

    @Override
    public int getUserFollowers(Long followerId) {
        return followRepository.getFollowersCount(followerId);
    }

    @Override
    public int getUserFollowing(Long followedId) {
        return followRepository.getFollowingCount(followedId);
    }
}
