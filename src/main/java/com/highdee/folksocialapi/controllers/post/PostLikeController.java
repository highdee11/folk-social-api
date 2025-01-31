package com.highdee.folksocialapi.controllers.post;

import com.highdee.folksocialapi.beans.post.PostLikeBean;
import com.highdee.folksocialapi.dto.request.post.PostLikeRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.services.post.post.PostService;
import com.highdee.folksocialapi.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/likes")
public class PostLikeController {

    private final PostLikeBean postLikeBean;

    public PostLikeController(PostLikeBean postLikeBean) {
        this.postLikeBean = postLikeBean;
    }

    @PostMapping("toggle")
    public ResponseEntity<RestResponse> toggleLike(@Valid  @RequestBody PostLikeRequest request) throws AuthentionException {
        postLikeBean.toggleLike(request);
        return ResponseEntity.status(200).body(RestResponse.success(null));
    }
}
