package com.highdee.folksocialapi.dto.response.post;

import com.highdee.folksocialapi.models.post.Tag;

import java.io.Serializable;

public class TagResponse implements Serializable {
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
