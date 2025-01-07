package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.request.post.PostMediaRequest;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.repositories.post.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final MediaService mediaService;

    public PostServiceImpl(PostRepository postRepository, MediaService mediaService) {
        this.postRepository = postRepository;
        this.mediaService = mediaService;
    }

    @Override
    public Post create(CreatePostRequest request, Long userId) {
        // Create Post
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(request.getContent());
        Post newPost = postRepository.save(post);

        // Create each request
        request.getMedia().forEach((PostMediaRequest postMediaRequest)-> {
            mediaService.create(postMediaRequest, newPost.getId());
        });

        return newPost;
    }
}
