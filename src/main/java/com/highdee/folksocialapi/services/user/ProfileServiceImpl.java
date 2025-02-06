package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.constants.CacheConst;
import com.highdee.folksocialapi.dto.request.auth.UpdateUserRequest;
import com.highdee.folksocialapi.dto.response.post.TagResponse;
import com.highdee.folksocialapi.dto.response.user.ProfileStatsResponse;
import com.highdee.folksocialapi.exceptions.handlers.ResourceNotFoundException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import com.highdee.folksocialapi.services.follow.FollowService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;

    private final FollowService followService;

    public ProfileServiceImpl(UserRepository userRepository, FollowService followService) {
        this.userRepository = userRepository;
        this.followService = followService;
    }

    @Override
    @CacheEvict(value = {CacheConst.LIST_INTEREST, CacheConst.USER_PREFERENCE}, key = "#userId")
    public void updateInterest(List<Tag> newInterest, Long userId) {
        User user = userRepository.getById(userId);
        Set<Tag> existingInterest = user.getInterests();

        // Remove interests that doesn't exist in the new interest
        Set<Tag> interestToRemove = existingInterest.stream().filter((tg)-> !newInterest.contains(tg)).collect(Collectors.toSet());
        user.getInterests().removeAll(interestToRemove);

        user.setInterests(newInterest);
        userRepository.save(user);
    }

    @Override
    @Cacheable(value = CacheConst.LIST_INTEREST, key = "#user.id")
    public Set<TagResponse> listInterest(User user) {
        return user.getInterests().stream().map(TagResponse::new).collect(Collectors.toSet());
    }

    @Override
    public void updateUser(Long userId, UpdateUserRequest request){
        User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        System.out.println(request.getFirstname());
        System.out.println(request.getFirstname().isEmpty());

        if(!request.getFirstname().isEmpty()) {
            user.setFirstName(request.getFirstname());
        }
        if(!request.getLastname().isEmpty()) {
            user.setLastName(request.getLastname());
        }
        if(request.getDob() != null) {
            user.setDob(request.getDob());
        }

        userRepository.save(user);
    }

    @Override
//    @Cacheable(value = CacheConst.PROFILE_STATS, key = "#userId")
    public ProfileStatsResponse getProfileStats(Long userId) {
        ProfileStatsResponse response = new ProfileStatsResponse();
        response.setFollowers(followService.getUserFollowers(userId));
        response.setFollowing(followService.getUserFollowing(userId));
        return response;
    }
}
