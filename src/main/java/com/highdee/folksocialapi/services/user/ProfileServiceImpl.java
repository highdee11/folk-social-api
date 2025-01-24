package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.dto.response.post.TagResponse;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @CacheEvict(value = {"listInterest", "userPreference"}, key = "#userId")
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
    @Cacheable(value = "listInterest", key = "#user.id")
    public Set<TagResponse> listInterest(User user) {
        return user.getInterests().stream().map(TagResponse::new).collect(Collectors.toSet());
    }


}
