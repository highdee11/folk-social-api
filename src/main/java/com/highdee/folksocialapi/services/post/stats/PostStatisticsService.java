package com.highdee.folksocialapi.services.post.stats;

import com.highdee.folksocialapi.dto.response.post.PostStatisticsResponse;
import com.highdee.folksocialapi.enums.PostStatisticTypes;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.PostStatistic;

import java.util.List;
import java.util.Map;

public interface PostStatisticsService {
    void updateStatCount(Post post, PostStatisticTypes type, Integer v);
    PostStatistic getSingleStat(Long PostId, PostStatisticTypes type);
    PostStatisticsResponse getSingleStatCached(Long PostId, PostStatisticTypes type);
}
