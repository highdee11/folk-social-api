package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.dto.request.post.PostMediaRequest;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.PostMedia;
import com.highdee.folksocialapi.repositories.post.PostMediaRepository;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements  MediaService{

    private final PostMediaRepository postMediaRepository;

    public MediaServiceImpl(PostMediaRepository postMediaRepository) {
        this.postMediaRepository = postMediaRepository;
    }

    public PostMedia create(PostMediaRequest request, Post post){
        PostMedia postMedia = new PostMedia();
        postMedia.setPost(post);
        postMedia.setType(request.getType());
        postMedia.setUrl(request.getUrl());

        return postMediaRepository.save(postMedia);
    }
}
