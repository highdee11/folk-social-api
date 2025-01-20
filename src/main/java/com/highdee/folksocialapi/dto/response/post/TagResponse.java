package com.highdee.folksocialapi.dto.response.post;

import com.highdee.folksocialapi.models.post.Tag;

public class TagResponse {
    private String name;

    public TagResponse(Tag tag) {
        this.name = tag.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
