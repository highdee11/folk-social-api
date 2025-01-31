package com.highdee.folksocialapi.beans.post;

import com.highdee.folksocialapi.dto.request.post.PostLikeRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.services.post.likes.PostLikesService;
import com.highdee.folksocialapi.services.post.post.PostService;
import com.highdee.folksocialapi.services.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class PostLikeBean {
    private final PostService postService;
    private final UserService userService;
    private final PostLikesService postLikesService;


    public PostLikeBean(PostService postService, UserService userService, PostLikesService postLikesService) {
        this.postService = postService;
        this.userService = userService;
        this.postLikesService = postLikesService;
    }

    public void toggleLike(PostLikeRequest request) throws AuthentionException {
        User user = userService.getLoggedInUser();
        Post post = postService.getOne(request.getPostId());

        postLikesService.togglePostLike(post, user);
    }
}
