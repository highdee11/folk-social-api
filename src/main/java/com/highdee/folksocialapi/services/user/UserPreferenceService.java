package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.dto.response.user.UserPreferenceResponse;
import com.highdee.folksocialapi.models.auth.User;

public interface UserPreferenceService {
    UserPreferenceResponse getUserPreference(User user);
}
