package com.highdee.folksocialapi.dto.request.post;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CreatePostRequest {
    @NotBlank
    public String content;

    public List<PostMediaRequest> media;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<PostMediaRequest> getMedia() {
        return media;
    }

    public void setMedia(List<PostMediaRequest> media) {
        this.media = media;
    }
}