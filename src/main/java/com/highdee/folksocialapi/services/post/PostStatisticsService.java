package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.models.post.Post;

public interface PostStatisticsService {
    void updateCommentCount(Post post, Integer v);
}
