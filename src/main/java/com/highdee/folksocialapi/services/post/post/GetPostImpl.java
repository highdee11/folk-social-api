package com.highdee.folksocialapi.services.post.post;

import com.highdee.folksocialapi.constants.AppConstants;
import com.highdee.folksocialapi.dto.request.post.ListPostRequest;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.repositories.post.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GetPostImpl implements GetPostService{
    private final PostRepository postRepository;

    public GetPostImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Page<Post> getFollowedPosts(ListPostRequest request, Long userId) {

        int page = request.getPage();
        int size = Math.min(request.getSize(), AppConstants.MAX_PAGE_SIZE);
        Sort sort = request.getOrder();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return postRepository.findAllThroughFollowing(pageRequest, userId);
    }

    @Override
    public Page<Post> getForYouPost(ListPostRequest request, Long userId) {

        int page = request.getPage();
        int size = Math.min(request.getSize(), AppConstants.MAX_PAGE_SIZE);
        Sort sort = request.getOrder();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return postRepository.findAll(pageRequest);
    }
}
