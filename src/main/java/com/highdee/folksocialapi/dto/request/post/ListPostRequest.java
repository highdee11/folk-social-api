package com.highdee.folksocialapi.dto.request.post;

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
        System.out.println(this.order);
        return order.equalsIgnoreCase("ASC") ?
                Sort.by(this.orderBy).ascending(): Sort.by(this.orderBy).descending();
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
