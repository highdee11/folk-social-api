package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.constants.CacheConst;
import com.highdee.folksocialapi.dto.response.user.UserPreferenceResponse;
import com.highdee.folksocialapi.models.auth.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService{

    @Override
    @Cacheable(cacheNames = CacheConst.USER_PREFERENCE, key = "#user.id")
    public UserPreferenceResponse getUserPreference(User user) {
        boolean hasInterest = !user.getInterests().isEmpty();
        return new UserPreferenceResponse(hasInterest);
    }

}
