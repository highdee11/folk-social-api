package com.highdee.folksocialapi.services.post.media;

import com.highdee.folksocialapi.dto.request.post.PostMediaRequest;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.PostMedia;

public interface MediaService {
    PostMedia create(PostMediaRequest request, Post post);
}
