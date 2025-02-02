package com.highdee.folksocialapi.services.post.post;

import com.highdee.folksocialapi.dto.request.post.ListPostRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.models.post.Post;
import org.springframework.data.domain.Page;

public interface GetPostService {
    Page<Post> getFollowedPosts(ListPostRequest request, Long userId);
    Page<Post> getForYouPost(ListPostRequest request, Long userId);

}
