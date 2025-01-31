package com.highdee.folksocialapi.repositories.post;

import com.highdee.folksocialapi.models.post.PostStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostStatisticRepository extends JpaRepository<PostStatistic, Long> {
    Optional<PostStatistic> findByPostIdAndType(Long postId, String type);
}
