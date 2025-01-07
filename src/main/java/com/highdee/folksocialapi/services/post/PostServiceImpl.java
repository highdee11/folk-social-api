package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.request.post.PostMediaRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.repositories.post.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final MediaService mediaService;

    public PostServiceImpl(PostRepository postRepository, MediaService mediaService) {
        this.postRepository = postRepository;
        this.mediaService = mediaService;
    }

    @Override
    public PostResponse getOne(Long postId) {
        Post post =  postRepository.getById(postId);
        return new PostResponse(post);
    }

    @Override
    public List<PostResponse> list(){
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(PostResponse::new).toList();
    }

    @Override
    public PostResponse create(CreatePostRequest request, Long userId) {
        // Create Post
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(request.getContent());
        final Post savedPost = postRepository.save(post);

        // Create each request
        request.getMedia().forEach((PostMediaRequest postMediaRequest)-> {
            mediaService.create(postMediaRequest, savedPost);
        });

        return new PostResponse(savedPost);
    }


    @Override
    public void delete(Long postId, Long authorId) throws CustomException {
        Post post = postRepository.getById(postId);

        if(!post.getUserId().equals(authorId)){
            throw new CustomException("You're not authorized to delete this post");
        }

        postRepository.delete(post);
    }
}
