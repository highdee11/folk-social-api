package com.highdee.folksocialapi.models.post;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "tags")
public class Tag {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
