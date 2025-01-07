package com.highdee.folksocialapi.dto.response.post;

import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.PostMedia;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {
    private Long id;
    public String content;
    public LocalDateTime createdAt;
    public List<PostMediaResponse> media;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.setMedia(post.getMediaList());
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMedia(List<PostMedia> media) {
        this.media = media.stream().map(PostMediaResponse::new).toList();
    }
}
