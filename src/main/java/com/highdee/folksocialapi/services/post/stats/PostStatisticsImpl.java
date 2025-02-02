package com.highdee.folksocialapi.services.post.stats;

import com.highdee.folksocialapi.dto.response.post.PostStatisticsResponse;
import com.highdee.folksocialapi.enums.PostStatisticTypes;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.PostStatistic;
import com.highdee.folksocialapi.repositories.post.PostStatisticRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostStatisticsImpl implements PostStatisticsService{

    private final PostStatisticRepository repository;

    public PostStatisticsImpl(PostStatisticRepository repository) {
        this.repository = repository;
    }

    @Override
    @CacheEvict(value = "postStatistics", key = "#type.name()+'_'+#post.id")
    public void updateStatCount(Post post, PostStatisticTypes type, Integer v) {
        Optional<PostStatistic> existingStat =
                repository.findByPostIdAndType(post.getId(), type.name());

        PostStatistic statistic = existingStat.orElse(new PostStatistic());

        if(statistic.getId() == null){ // Create new stat
            Map<String, Object> data = new HashMap<>();
            data.put("count", 0);

            statistic.setPost(post);
            statistic.setType(type.name());
            statistic.setDataFromMap(data);
        }
        // Update stats
        Map<String, Object> data = statistic.getDataAsMap();
        Integer count = Integer.parseInt(data.getOrDefault("count", 0).toString());
        count += v;
        data.put("count", Math.max(count, 0) );

        statistic.setDataFromMap(data);
        repository.save(statistic);
    }

    @Override
    public PostStatistic getSingleStat(Long id, PostStatisticTypes type) {
        return repository.findByPostIdAndType(id, type.name()).orElse(null);
    }

    @Cacheable(value = "postStatistics", key = "#type.name()+'_'+#id")
    public PostStatisticsResponse getSingleStatCached(Long id, PostStatisticTypes type) {
        Optional<PostStatistic> statistic = repository.findByPostIdAndType(id, type.name());
        return statistic.map(PostStatisticsResponse::new)
                .orElseGet(() -> PostStatisticsResponse.EmptyPostStatisticsResponse(type));

    }
}
