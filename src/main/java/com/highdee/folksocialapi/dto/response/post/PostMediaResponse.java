package com.highdee.folksocialapi.dto.response.post;

import com.highdee.folksocialapi.models.post.PostMedia;

public class PostMediaResponse {
    public String type;
    public String url;

    public PostMediaResponse(PostMedia postMedia) {
        this.type = postMedia.getType();
        this.url = postMedia.getUrl();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
