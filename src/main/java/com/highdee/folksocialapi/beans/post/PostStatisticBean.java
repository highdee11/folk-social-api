package com.highdee.folksocialapi.beans.post;

import com.highdee.folksocialapi.enums.PostStatisticTypes;
import com.highdee.folksocialapi.services.post.stats.PostStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PostStatisticBean {

    @Autowired
    private PostStatisticsService postStatisticsService;

    public Map<String, Object> getPostStats(Long postId) {

        List<PostStatisticTypes> types = List.of(PostStatisticTypes.POST_LIKES, PostStatisticTypes.COMMENT_COUNT);
        Map<String, Object> statistics = new HashMap<>();

        // Retrieve cached stats
        for(PostStatisticTypes type: types){
            statistics.put(type.name().toLowerCase(), postStatisticsService.getSingleStatCached(postId, type));
        }

        return statistics;
    }
}
