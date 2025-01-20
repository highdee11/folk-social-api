package com.highdee.folksocialapi.controllers.post;

import com.highdee.folksocialapi.constants.AppConstants;
import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.request.post.ListPostRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.exceptions.handlers.ResourceNotFoundException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.services.post.PostService;
import com.highdee.folksocialapi.services.tag.TagService;
import com.highdee.folksocialapi.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    private final UserService userService;


    public PostController(PostService postService, UserService userService){
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<PostResponse>> getOne(@PathVariable Long id){
        PostResponse post = postService.getOne(id);

        return ResponseEntity.status(200).body(RestResponse.success(post));
    }

    @GetMapping
    public ResponseEntity<RestResponse<Page<PostResponse>>> list(@ModelAttribute ListPostRequest request){
        Page<PostResponse> postResponseList = postService.list(request);

        return ResponseEntity.status(200).body(RestResponse.success(postResponseList));
    }

    @PostMapping
    public ResponseEntity<RestResponse<PostResponse>> create(@Valid @RequestBody CreatePostRequest request){

        // Retrieve the user
        User user = userService.getLoggedInUser().orElseThrow(ResourceNotFoundException::new);

        // Create a new post
        PostResponse post = postService.create(request, user.getId());

        // Return success response
        return ResponseEntity.status(200).body(RestResponse.success(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Object>> delete(@PathVariable long id) throws CustomException {
        User user = userService.getLoggedInUser().orElseThrow(ResourceNotFoundException::new);
        postService.delete(id, user.getId());

        return ResponseEntity.status(200).body(RestResponse.success(null));
    }
}
