package com.highdee.folksocialapi.beans.post;

import com.highdee.folksocialapi.dto.request.post.PostLikeRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.services.auth.AuthService;
import com.highdee.folksocialapi.services.post.likes.PostLikesService;
import com.highdee.folksocialapi.services.post.post.PostService;
import com.highdee.folksocialapi.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostLikeBean {
    private final PostService postService;
    private final AuthService authService;
    private final PostLikesService postLikesService;


    public PostLikeBean(PostService postService, AuthService authService, PostLikesService postLikesService) {
        this.postService = postService;
        this.authService = authService;
        this.postLikesService = postLikesService;
    }

    public void toggleLike(PostLikeRequest request) throws AuthentionException {
        User user = authService.getLoggedInUser();
        Post post = postService.getOne(request.getPostId());

        postLikesService.togglePostLike(post, user);
    }
}
