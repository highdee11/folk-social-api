package com.highdee.folksocialapi.controllers.post;

import com.highdee.folksocialapi.dto.request.post.SearchTagRequest;
import com.highdee.folksocialapi.dto.request.user.UpdateInterestRequest;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.dto.response.post.TagResponse;
import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.services.tag.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("")
    public ResponseEntity<RestResponse<Object>> listTags(@ModelAttribute SearchTagRequest request){
        List<TagResponse> list = tagService.listTags(request).stream().map(TagResponse::new).toList();
        return ResponseEntity.status(200).body(RestResponse.success(list));
    }
}
