package com.highdee.folksocialapi.services.post.likes;

import com.highdee.folksocialapi.enums.PostStatisticTypes;
import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.models.post.Post;
import com.highdee.folksocialapi.models.post.PostStatistic;
import com.highdee.folksocialapi.repositories.post.PostLike;
import com.highdee.folksocialapi.repositories.post.PostLikeRepository;
import com.highdee.folksocialapi.services.post.stats.PostStatisticsService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class PostLikesServiceImpl implements PostLikesService {

    private final PostLikeRepository repository;
    private final PostStatisticsService postStatisticsService;

    public PostLikesServiceImpl(PostLikeRepository repository, PostStatisticsService postStatisticsService) {
        this.repository = repository;
        this.postStatisticsService = postStatisticsService;
    }

    @Override
    public void togglePostLike(Post post, User user) {
       Optional<PostLike> existingLike =
               repository.findByPostIdAndUserId(post.getId(), user.getId());
       existingLike.ifPresentOrElse(
           repository::delete,
           ()-> { // Save like
           PostLike like = new PostLike(post, user);
           repository.save(like);
       });

        // Update the post like
        this.updatePostLikeCount(post, existingLike.isEmpty() ? 1 : -1);
    }

    @Override
    @CacheEvict(value = "postLikeCount", key = "#post.id")
    public void updatePostLikeCount(Post post, int v) {
        postStatisticsService.updateStatCount(post,
                PostStatisticTypes.POST_LIKES, v);
    }

    @Override
    @Cacheable(value = "postLikeCount", key = "#post.id")
    public int getPostLikeCount(Post post) {
        try {
            PostStatistic statistic = postStatisticsService.getSingleStat(post, PostStatisticTypes.POST_LIKES);
            if(statistic != null){
                return (int) statistic.getDataAsMap().get("count");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
