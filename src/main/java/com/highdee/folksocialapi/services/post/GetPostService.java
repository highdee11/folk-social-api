package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.dto.request.post.ListPostRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import org.springframework.data.domain.Page;

public interface GetPostService {
    Page<PostResponse> getFollowedPosts(ListPostRequest request, Long userId);
    Page<PostResponse> getForYouPost(ListPostRequest request, Long userId);

}
