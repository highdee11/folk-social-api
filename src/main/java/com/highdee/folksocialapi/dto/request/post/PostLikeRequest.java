package com.highdee.folksocialapi.dto.request.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLikeRequest {
    @JsonProperty("post_id")
    private Long postId;
}
