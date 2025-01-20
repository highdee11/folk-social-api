package com.highdee.folksocialapi.dto.request.post;

import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class CreatePostRequest {
    @NotBlank
    public String content;

    // Replying to
    public Long parent_id;

    public List<PostMediaRequest> media = new ArrayList<>();

    public List<String> tags = new ArrayList<>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return this.parent_id;
    }

    public void setParentId(Long parentId) {
        this.parent_id = parentId;
    }

    public List<PostMediaRequest> getMedia() {
        return media;
    }

    public void setMedia(List<PostMediaRequest> media) {
        this.media = media;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
