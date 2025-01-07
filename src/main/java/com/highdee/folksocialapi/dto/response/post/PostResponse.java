package com.highdee.folksocialapi.dto.response.post;

import com.highdee.folksocialapi.models.post.PostMedia;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {
    public String content;
    public LocalDateTime createdAt;
    public List<PostMediaResponse> media;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.createdAt = created_at;
    }

    public List<PostMediaResponse> getMedia() {
        return media;
    }

    public void setMedia(List<PostMedia> media) {
        this.media = media.stream().map((PostMedia md)-> {
            PostMediaResponse postMediaResponse = new PostMediaResponse();
            postMediaResponse.setType(md.getType());
            postMediaResponse.setUrl(md.getUrl());
            return postMediaResponse;
        }).toList();
    }
}
