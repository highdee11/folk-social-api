package com.highdee.folksocialapi.services.post;

import com.highdee.folksocialapi.enums.PostStatisticTypes;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.PostStatistic;
import com.highdee.folksocialapi.repositories.post.PostStatisticRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PostStatisticsImpl implements PostStatisticsService{

    private final PostStatisticRepository repository;

    public PostStatisticsImpl(PostStatisticRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateCommentCount(Post post, Integer v) {
        String type = PostStatisticTypes.COMMENT_COUNT.name();
        Optional<PostStatistic> existingStat =
                repository.findByPostIdAndType(post.getId(), type);

        PostStatistic statistic = existingStat.orElse(new PostStatistic());

        if(statistic.getId() == null){
            Map<String, Object> data = new HashMap<>();
            data.put("count", 0);

            statistic.setPost(post);
            statistic.setType(type);
            statistic.setDataFromMap(data);
        }
        Map<String, Object> data = statistic.getDataAsMap();
        Integer count = Integer.parseInt(data.getOrDefault("count", 0).toString());
        count += v;
        data.put("count", count);

        statistic.setDataFromMap(data);

        repository.save(statistic);
    }
}
