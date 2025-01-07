package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.models.post.Post;

import java.util.List;

public interface PostService {
    PostResponse getOne(Long postId);
    List<PostResponse> list();
    PostResponse create(CreatePostRequest request, Long userId);
    void delete(Long postId, Long authorId) throws CustomException;
}
