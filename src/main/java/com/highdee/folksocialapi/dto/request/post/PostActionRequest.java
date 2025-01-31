package com.highdee.folksocialapi.dto.request.post;

import jakarta.validation.constraints.NotBlank;

public class PostActionRequest {

    @NotBlank
    private String type;

    @NotBlank
    private Long postId;

    @NotBlank
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
