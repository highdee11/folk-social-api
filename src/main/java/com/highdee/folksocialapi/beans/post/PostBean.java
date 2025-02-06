package com.highdee.folksocialapi.beans.post;

import com.highdee.folksocialapi.constants.CacheConst;
import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.request.post.ListPostRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.services.post.likes.PostLikesServiceImpl;
import com.highdee.folksocialapi.services.post.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class PostBean {

    @Autowired
    private final PostService postService;
    @Autowired
    private PostStatisticBean postStatisticBean;

    @Autowired
    private PostLikesServiceImpl postLikesService;

    public PostBean(PostService postService) {
        this.postService = postService;
    }

    @Cacheable(value = CacheConst.SINGLE_POST, key = "#postId")
    public PostResponse getOne(Long postId) {
        // Get Post
        Post post = postService.getOne(postId);

        // Get Post Stats
        Map<String, Object> stats = postStatisticBean.getPostStats(postId);
        PostResponse response = new PostResponse(post);
        response.setStatistics(stats);

        return response;
    }

    public Page<PostResponse> list(ListPostRequest request, Long userId){
        return postService.list(request, userId).map(post -> { // Get Stats for each
            Map<String, Object> stats = postStatisticBean.getPostStats(post.getId());
            PostResponse response = new PostResponse(post);
            response.setStatistics(stats);
            return response;
        });
    }

    public PostResponse create(CreatePostRequest request, Long userId) {
        Post post = postService.create(request, userId);
        return new PostResponse(post);
    }

    @CacheEvict(value = CacheConst.SINGLE_POST, key = "#postId")
    public void delete(Long postId, Long authorId) throws CustomException {
       postService.delete(postId, authorId);
    }

}
