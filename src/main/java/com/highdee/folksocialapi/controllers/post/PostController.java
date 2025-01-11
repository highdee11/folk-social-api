package com.highdee.folksocialapi.controllers.post;

import com.highdee.folksocialapi.constants.AppConstants;
import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.services.post.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<RestResponse<Page<PostResponse>>> list(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER+"") int page,
            @RequestParam(defaultValue =  AppConstants.DEFAULT_PAGE_SIZE+"") int size
    ){
        Page<PostResponse> postResponseList = postService.list(PageRequest.of(page, size));

        return ResponseEntity.status(200).body(RestResponse.success(postResponseList));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Object>> delete(@PathVariable long id) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        postService.delete(id, Long.parseLong(userId));

        return ResponseEntity.status(200).body(RestResponse.success(null));
    }
}
