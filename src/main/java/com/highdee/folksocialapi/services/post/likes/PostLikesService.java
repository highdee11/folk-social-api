package com.highdee.folksocialapi.services.post.likes;

import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Post;

public interface PostLikesService {
    void togglePostLike(Post post, User user);
    void updatePostLikeCount(Post post, int v);
    int getPostLikeCount(Post post);
    boolean userLikedPost(Long postId, Long userId);
}
