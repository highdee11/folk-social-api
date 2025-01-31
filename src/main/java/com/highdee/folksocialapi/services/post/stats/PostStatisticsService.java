package com.highdee.folksocialapi.services.post.stats;

import com.highdee.folksocialapi.enums.PostStatisticTypes;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.PostStatistic;

public interface PostStatisticsService {
    void updateStatCount(Post post, PostStatisticTypes type, Integer v);
    PostStatistic getSingleStat(Post post, PostStatisticTypes type);
}
