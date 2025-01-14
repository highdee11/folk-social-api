package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.constants.AppConstants;
import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.request.post.ListPostRequest;
import com.highdee.folksocialapi.dto.request.post.PostMediaRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.exceptions.handlers.ResourceNotFoundException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import com.highdee.folksocialapi.repositories.post.PostRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MediaService mediaService;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, MediaService mediaService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.mediaService = mediaService;
        this.userRepository = userRepository;
    }

    @Override
    public PostResponse getOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);
        return new PostResponse(post);
    }

    @Override
    public Page<PostResponse> list(ListPostRequest request){

        int page = request.getPage();
        int size = Math.min(request.getSize(), AppConstants.MAX_PAGE_SIZE);
        Sort sort = request.getOrder();

        System.out.println(request.getAuthorId());
        if(request.getAuthorId() != null){
            return postRepository.findAllByUser_id(PageRequest.of(page, size, sort), request.getAuthorId())
                    .map(PostResponse::new);
        }

        if(request.getPostId() != null){
            return postRepository.findAllByParent_id(PageRequest.of(page, size, sort), request.getPostId())
                    .map(PostResponse::new);
        }

        return postRepository.findAll(PageRequest.of(page, size, sort))
                .map(PostResponse::new);
    }

    @Override
    public PostResponse create(CreatePostRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);

        // Create Post
        Post post = new Post();
        post.setUser(user);
        post.setContent(request.getContent());
        if(request.getParentId() != null){
            Post parentPost = postRepository.findById(request.getParentId()).orElseThrow(ResourceNotFoundException::new);
            post.setParent(parentPost);
        }

        final Post savedPost = postRepository.save(post);

        // Create each media
        request.getMedia().forEach((PostMediaRequest postMediaRequest)-> {
            mediaService.create(postMediaRequest, savedPost);
        });

        return new PostResponse(savedPost);
    }


    @Override
    public void delete(Long postId, Long authorId) throws CustomException {
        Post post = postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);

        if(!post.getUserId().equals(authorId)){
            throw new CustomException("You're not authorized to delete this post");
        }

        postRepository.delete(post);
    }
}
