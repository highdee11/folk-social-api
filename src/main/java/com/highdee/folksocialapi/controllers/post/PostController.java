package com.highdee.folksocialapi.controllers.post;

import com.highdee.folksocialapi.beans.post.GetPostBean;
import com.highdee.folksocialapi.beans.post.PostBean;
import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.request.post.ListPostRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.AuthentionException;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.services.post.post.GetPostService;
import com.highdee.folksocialapi.services.post.post.PostService;
import com.highdee.folksocialapi.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostBean postBean;
    private final UserService userService;
    private final GetPostService getPostService;

    @Autowired
    private GetPostBean getPostBean;

    public PostController(PostBean postBean, UserService userService, GetPostService getPostService){
        this.postBean = postBean;
        this.userService = userService;
        this.getPostService = getPostService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<PostResponse>> getOne(@PathVariable Long id){
        PostResponse post = postBean.getOne(id);

        return ResponseEntity.status(200).body(RestResponse.success(post));
    }

    @GetMapping("")
    public ResponseEntity<RestResponse<Page<PostResponse>>> list(@ModelAttribute ListPostRequest request) throws AuthentionException {
        // Retrieve the user
        User user = userService.getLoggedInUser();

        Page<PostResponse> postResponseList = postBean.list(request, user.getId());
        return ResponseEntity.status(200).body(RestResponse.success(postResponseList));
    }

    @GetMapping("/followed")
    public ResponseEntity<RestResponse<Page<PostResponse>>> listFollowedPost(@ModelAttribute ListPostRequest request) throws AuthentionException {
        // Retrieve the user
        User user = userService.getLoggedInUser();

        Page<PostResponse> postResponseList = getPostBean.getFollowedPosts(request, user.getId());
        return ResponseEntity.status(200).body(RestResponse.success(postResponseList));
    }

    @GetMapping("/foryou")
    public ResponseEntity<RestResponse<Page<PostResponse>>> listForYouPost(@ModelAttribute ListPostRequest request) throws AuthentionException {
        // Retrieve the user
        User user = userService.getLoggedInUser();

        Page<PostResponse> postResponseList = getPostBean.getForYouPost(request, user.getId());
        return ResponseEntity.status(200).body(RestResponse.success(postResponseList));
    }

    @PostMapping
    public ResponseEntity<RestResponse<PostResponse>> create(@Valid @RequestBody CreatePostRequest request) throws AuthentionException {
        // Retrieve the user
        User user = userService.getLoggedInUser();

        // Create a new post
        PostResponse post = postBean.create(request, user.getId());

        // Return success response
        return ResponseEntity.status(200).body(RestResponse.success(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Object>> delete(@PathVariable long id) throws CustomException, AuthentionException {
        User user = userService.getLoggedInUser();
        postBean.delete(id, user.getId());

        return ResponseEntity.status(200).body(RestResponse.success(null));
    }
}
