package com.highdee.folksocialapi.services.user;

import com.highdee.folksocialapi.models.post.Tag;

import java.util.List;
import java.util.Set;

public interface ProfileService {
    void updateInterest(List<Tag> tags, Long userId);
    Set<Tag> listInterest(Long userId);
}
