package com.highdee.folksocialapi.enums;

public enum PostStatisticTypes {

    COMMENT_COUNT("comment_count"),
    POST_LIKES("post_likes");

    private final String type;

    PostStatisticTypes(String type) {
        this.type = type;
    }
}
