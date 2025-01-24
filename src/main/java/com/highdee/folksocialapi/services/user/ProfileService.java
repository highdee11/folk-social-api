package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.dto.response.post.TagResponse;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Tag;

import java.util.List;
import java.util.Set;

public interface ProfileService {
    void updateInterest(List<Tag> tags, Long userId);
    Set<TagResponse> listInterest(User user);
}
