package com.highdee.folksocialapi.services.post.post;

import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.request.post.ListPostRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.models.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Post getOne(Long postId);

    Page<Post> list(ListPostRequest request, Long id);

    Post create(CreatePostRequest request, Long userId);
    void delete(Long postId, Long authorId) throws CustomException;
}
