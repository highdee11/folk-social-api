package com.highdee.folksocialapi.dto.response.post;

import com.highdee.folksocialapi.dto.response.user.UserResponse;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.PostMedia;
import com.highdee.folksocialapi.models.post.Tag;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class PostResponse implements Serializable {
    private Long id;
    public String content;

    public UserResponse author;
    public LocalDateTime createdAt;
    public List<PostMediaResponse> media;
    @Nullable
    public PostResponse parentPost;

    public List<TagResponse> tags;

    public PostResponse() {}

    public PostResponse(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.author = new UserResponse(post.getUser());

        this.setTags(post.getTags());
        this.setMedia(post.getMediaList());
//        this.setParentPost(post.getParent());
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

    public void setParentPost(@Nullable Post parentPost){
        if(parentPost == null) return;
        this.parentPost = new PostResponse(parentPost);
    }
    public void setMedia(List<PostMedia> media) {
        this.media = media.stream().map(PostMediaResponse::new).toList();
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags.stream().map(TagResponse::new).toList();
    }
}
