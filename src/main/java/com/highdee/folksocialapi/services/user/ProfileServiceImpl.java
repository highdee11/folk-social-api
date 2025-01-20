package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateInterest(List<Tag> tags, Long userId) {
        User user = userRepository.getById(userId);
        user.setInterests(tags);
        userRepository.save(user);
    }

    @Override
    public Set<Tag> listInterest(Long userId) {
        User user = userRepository.getById(userId);
        return user.getInterests();
    }
}
