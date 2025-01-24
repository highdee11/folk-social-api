package com.highdee.folksocialapi.dto.response.post;

import com.highdee.folksocialapi.models.post.Tag;

import java.io.Serializable;

public class TagResponse implements Serializable {
    private Long id;
    private String name;

    public TagResponse(Tag tag) {
        this.name = tag.getName();
        this.id = tag.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
