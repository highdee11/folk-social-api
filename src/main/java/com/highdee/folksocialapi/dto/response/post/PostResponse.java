package com.highdee.folksocialapi.dto.response.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.highdee.folksocialapi.dto.response.user.UserResponse;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.PostMedia;
import com.highdee.folksocialapi.models.post.Tag;
import lombok.Getter;
import lombok.Setter;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PostResponse implements Serializable {
    private Long id;
    private String content;
    private UserResponse author;
    private List<PostMediaResponse> media;
    private List<TagResponse> tags;
    private String since;
    private Map<String, Object> statistics;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Nullable
    @JsonProperty("parent_post")
    private PostResponse parentPost;

    public String getSince() {
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(createdAt);
    }

    private PostResponse() {}

    public PostResponse(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.author = new UserResponse(post.getUser());

        this.setTags(post.getTags());
        this.setMedia(post.getMediaList());
//        this.setParentPost(post.getParent());
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
