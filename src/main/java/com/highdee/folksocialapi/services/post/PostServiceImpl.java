package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.constants.AppConstants;
import com.highdee.folksocialapi.dto.request.post.CreatePostRequest;
import com.highdee.folksocialapi.dto.request.post.PostMediaRequest;
import com.highdee.folksocialapi.dto.response.post.PostResponse;
import com.highdee.folksocialapi.exceptions.handlers.CustomException;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import com.highdee.folksocialapi.repositories.post.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Post post =  postRepository.getById(postId);
        return new PostResponse(post);
    }

    @Override
    public Page<PostResponse> list(Pageable pageable){

        int page = pageable.getPageNumber();
        int size = Math.min(pageable.getPageSize(), AppConstants.MAX_PAGE_SIZE);

        Page<PostResponse> posts = postRepository.findAll(PageRequest.of(page, size))
                .map(PostResponse::new);

        return posts;
    }

    @Override
    public PostResponse create(CreatePostRequest request, Long userId) {
        User user = userRepository.getById(userId);
        // Create Post
        Post post = new Post();
        post.setUser(user);
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
