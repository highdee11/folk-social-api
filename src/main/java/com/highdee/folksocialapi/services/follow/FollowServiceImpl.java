package com.highdee.folksocialapi.services.follow;

import com.highdee.folksocialapi.dto.request.CreateFollowRequest;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.exceptions.handlers.ResourceNotFoundException;
import com.highdee.folksocialapi.models.UserFollow;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.repositories.FollowRepository;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import com.highdee.folksocialapi.services.user.UserService;
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
            throw new CustomException("You're already a following "+followedUser.getFirstName());
        }

        UserFollow userFollow = new UserFollow();
        userFollow.setFollower(follower);
        userFollow.setFollowing(followedUser);

        followRepository.save(userFollow);
    }

    @Override
    public boolean checkIfAlreadyFollower(Long followerId, Long FollowedId) {
        UserFollow userFollow =  followRepository.findByFollowedIdAndFollowerId(FollowedId, followerId);
        return userFollow != null;
    }
}
