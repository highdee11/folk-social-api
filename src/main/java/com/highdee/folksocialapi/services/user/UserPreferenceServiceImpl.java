package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.dto.response.user.UserPreferenceResponse;
import com.highdee.folksocialapi.models.auth.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService{
    @Override
    @Cacheable(cacheNames = "userPreference", key = "#user.id")
    public UserPreferenceResponse getUserPreference(User user) {
        boolean hasInterest = !user.getInterests().isEmpty();

        return new UserPreferenceResponse(hasInterest);
    }
}
