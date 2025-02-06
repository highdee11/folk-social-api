package com.highdee.folksocialapi.repositories;

import com.highdee.folksocialapi.models.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<UserFollow, Long> {
    UserFollow findByFollowedIdAndFollowerId(Long FollowedId, Long FollowerId);

    @Query(value = "SELECT count(*) from user_followers WHERE followed_id = :followerId", nativeQuery = true)
    int getFollowersCount(Long followerId);

    @Query(value = "SELECT count(*) from user_followers WHERE follower_id = :followedId", nativeQuery = true)
    int getFollowingCount(Long followedId);

}
