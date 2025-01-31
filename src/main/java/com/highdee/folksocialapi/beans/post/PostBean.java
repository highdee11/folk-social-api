package com.highdee.folksocialapi.beans.post;

import com.highdee.folksocialapi.constants.AppConstants;
import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.request.post.ListPostRequest;
import com.highdee.folksocialapi.dto.request.post.PostMediaRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.exceptions.handlers.ResourceNotFoundException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.services.post.post.PostService;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostBean {
    private final PostService postService;

    public PostBean(PostService postService) {
        this.postService = postService;
    }

    @Cacheable(value = "oneProduct", key = "#postId")
    public PostResponse getOne(Long postId) {
        Post post = postService.getOne(postId);
        return new PostResponse(post);
    }

    public Page<PostResponse> list(ListPostRequest request, Long userId){
        return postService.list(request, userId).map(PostResponse::new);
    }

    public PostResponse create(CreatePostRequest request, Long userId) {
        Post post = postService.create(request, userId);
        return new PostResponse(post);
    }

    @CacheEvict(value = "oneProduct", key = "#postId")
    public void delete(Long postId, Long authorId) throws CustomException {
       postService.delete(postId, authorId);
    }

}
