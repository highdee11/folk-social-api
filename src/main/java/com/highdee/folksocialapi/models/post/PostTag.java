package com.highdee.folksocialapi.models.post;

import jakarta.persistence.*;

@Entity
@Table(name = "post_tag")
public class PostTag {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag interest;

}
