package com.highdee.folksocialapi.services.tag;

import com.highdee.folksocialapi.dto.request.post.SearchTagRequest;
import com.highdee.folksocialapi.models.post.Tag;

import java.util.List;

public interface TagService {
    Tag createTag(String name);

    List<Tag> createAllTags(List<String> names);

    String normalizeTag(String name);

    List<Tag> listTags(SearchTagRequest request);
}
