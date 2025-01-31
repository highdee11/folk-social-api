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
import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.repositories.auth.UserRepository;
import com.highdee.folksocialapi.repositories.post.PostRepository;
import com.highdee.folksocialapi.services.tag.TagService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final MediaService mediaService;

    private final UserRepository userRepository;

    private final TagService tagService;

    private final PostStatisticsService statisticsService;

    public PostServiceImpl(PostRepository postRepository,
                           MediaService mediaService,
                           UserRepository userRepository,
                           TagService tagService,
                           PostStatisticsService statisticsService) {
        this.postRepository = postRepository;
        this.mediaService = mediaService;
        this.userRepository = userRepository;
        this.tagService = tagService;
        this.statisticsService = statisticsService;
    }


    @Override
    @Cacheable(value = "oneProduct", key = "#postId")
    public PostResponse getOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);
        return new PostResponse(post);
    }

    @Override
    public Page<PostResponse> list(ListPostRequest request, Long userId){

        int page = request.getPage();
        int size = Math.min(request.getSize(), AppConstants.MAX_PAGE_SIZE);
        Sort sort = request.getOrder();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if(request.getAuthorId() != null){
            return postRepository.findAllByUser_id(pageRequest, request.getAuthorId())
                    .map(PostResponse::new);
        }

        if(request.getPostId() != null){
            return postRepository.findAllByParent_id(pageRequest, request.getPostId())
                    .map(PostResponse::new);
        }

        return postRepository.findAll(pageRequest).map(PostResponse::new);

    }

    @Override
    @Transactional
    public PostResponse create(CreatePostRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        Post parentPost = null;
        
        // Create Post
        Post post = new Post();
        post.setUser(user);
        post.setContent(request.getContent());

        // if post is a comment
        if(request.getParentId() != null){
            parentPost = postRepository.findById(request.getParentId()).orElseThrow(ResourceNotFoundException::new);
            post.setParent(parentPost);
        }

        // Create and normalise tags
        if(!request.getTags().isEmpty()) {
            List<Tag> tags = tagService.createAllTags(request.getTags());
            post.setTags(tags);
        }

        // Save Post
        final Post savedPost = postRepository.save(post);

        // Create each media
        request.getMedia().forEach((PostMediaRequest postMediaRequest)-> {
            mediaService.create(postMediaRequest, savedPost);
        });

        if(parentPost != null){
            statisticsService.updateCommentCount(parentPost, 1);
        }

        return new PostResponse(savedPost);
    }


    @Override
    @CacheEvict(value = "oneProduct", key = "#postId")
    public void delete(Long postId, Long authorId) throws CustomException {
        Post post = postRepository.findById(postId).orElseThrow(ResourceNotFoundException::new);
        Post parentPost = post.getParent();

        if(!post.getUserId().equals(authorId)){
            throw new CustomException("You're not authorized to delete this post");
        }

        // Update parent post stats
        if(parentPost != null){
            statisticsService.updateCommentCount(parentPost, -1);
        }

        postRepository.delete(post);
    }
}
