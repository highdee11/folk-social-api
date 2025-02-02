package com.highdee.folksocialapi.controllers.post;

import com.highdee.folksocialapi.beans.post.PostStatisticBean;
import com.highdee.folksocialapi.dto.response.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class PostStatisticController {

    @Autowired
    private PostStatisticBean postStatisticBean;

    @GetMapping("/post/{id}")
    public ResponseEntity<RestResponse<Map<String, Object>>> postStat(@PathVariable Long id){
        Map<String, Object> stats = postStatisticBean.getPostStats(id);

        return ResponseEntity.status(200).body(RestResponse.success(stats));
    }

}
