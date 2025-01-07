package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.models.post.Post;

public interface PostService {
    PostResponse create(CreatePostRequest request, Long userId);

    PostResponse getOne(Long postId);
}
