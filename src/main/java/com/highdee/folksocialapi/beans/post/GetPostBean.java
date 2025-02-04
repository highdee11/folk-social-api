package com.highdee.folksocialapi.beans.post;

import com.highdee.folksocialapi.dto.request.post.ListPostRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.services.post.likes.PostLikesServiceImpl;
import com.highdee.folksocialapi.services.post.post.GetPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GetPostBean {

    @Autowired
    private GetPostService getPostService;

    @Autowired
    private PostStatisticBean postStatisticBean;

    @Autowired
    private PostLikesServiceImpl postLikesService;


    public Page<PostResponse> getFollowedPosts(ListPostRequest request, Long userId) {
        return getPostService.getFollowedPosts(request, userId).map(post -> {
            PostResponse response = new PostResponse(post);
            response.setStatistics(postStatisticBean.getPostStats(post.getId()));
            response.setHasLiked(postLikesService.userLikedPost(post.getId(), userId));
            return response;
        });
    }


    public Page<PostResponse> getForYouPost(ListPostRequest request, Long userId) {
        return getPostService.getForYouPost(request, userId).map(post -> {
            PostResponse response = new PostResponse(post);
            response.setStatistics(postStatisticBean.getPostStats(post.getId()));
            return response;
        });
    }
}
