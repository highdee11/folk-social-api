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
    @CacheEvict(value = "userLikedPost", key = "#post.getId() +'_'+#user.getId()")
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
    public void updatePostLikeCount(Post post, int v) {
        postStatisticsService.updateStatCount(post, PostStatisticTypes.POST_LIKES, v);
    }


    @Override
    public int getPostLikeCount(Post post) {
        try {
            PostStatistic statistic = postStatisticsService.getSingleStat(post.getId(), PostStatisticTypes.POST_LIKES);
            if(statistic != null){
                return (int) statistic.getDataAsMap().get("count");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    @Cacheable(value = "userLikedPost", key = "#postId +'_'+#userId")
    public boolean userLikedPost(Long postId, Long userId) {
        Optional<PostLike> existingLike =
                repository.findByPostIdAndUserId(postId, userId);

        return existingLike.isPresent();
    }
}
