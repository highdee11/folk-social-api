package com.highdee.folksocialapi.dto.request.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.highdee.folksocialapi.constants.AppConstants;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

public class ListPostRequest {
    private int page = AppConstants.DEFAULT_PAGE_NUMBER;
    private int size = AppConstants.DEFAULT_PAGE_SIZE;
    private String order = AppConstants.DEFAULT_PAGE_ORDER;
    private String orderBy = AppConstants.DEFAULT_PAGE_ORDERBY;
    private Long post_id;
    private Long author_id;

    @JsonProperty("feed_type")
    private String feedType;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getPostId() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public Long getAuthorId() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    public Sort getOrder() {
        return order.equalsIgnoreCase("ASC") ?
                Sort.by(this.orderBy).ascending(): Sort.by(this.orderBy).descending();
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }
}
