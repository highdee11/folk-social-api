package com.highdee.folksocialapi.controllers.post;

import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.services.post.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<PostResponse>> getOne(@PathVariable Long id){
        PostResponse post = postService.getOne(id);

        return ResponseEntity.status(200).body(RestResponse.success(post));
    }

    @PostMapping
    public ResponseEntity<RestResponse<PostResponse>> create(@Valid @RequestBody CreatePostRequest request){

        // Retrieve the user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // Create a new post
        PostResponse post = postService.create(request, Long.parseLong(userId));

        // Return success response
        return ResponseEntity.status(200).body(RestResponse.success(post));
    }
}
