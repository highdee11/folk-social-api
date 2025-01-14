package com.highdee.folksocialapi.repositories;

import com.highdee.folksocialapi.models.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<UserFollow, Long> {
    public UserFollow findByFollowedIdAndFollowerId(Long FollowedId, Long FollowerId);
}
