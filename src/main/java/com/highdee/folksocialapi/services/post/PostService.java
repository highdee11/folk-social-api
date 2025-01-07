package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.models.post.Post;

public interface PostService {
    Post create(CreatePostRequest request, Long userId);
}
